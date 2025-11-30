[TOC]

## Woche 8

```java
package kickstart.festival;

import kickstart.location.Location;
import kickstart.location.LocationIdentifier;
import kickstart.location.LocationRepository;

import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class FestivalControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FestivalRepository festivalRepo;

    @MockBean
    LocationRepository locations;

    @MockBean
    FestivalManagement festivalManagement;

    @Test
    void editFestival_gibtEditSeiteZurueck() throws Exception {

        // Location
        Location location = new Location("Ort-Name", "Strasse 1", 500);

        //  Festival
        FestivalIdentifier id = new FestivalIdentifier(UUID.randomUUID());
        Festival fest = new Festival(
                "TestFest",
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 3, 0, 0),
                location
        );

        when(festivalRepo.findById(id)).thenReturn(Optional.of(fest));
        when(locations.findAll()).thenReturn(List.of(loc));

        // Aufruf: GET /festival/edit/{id}
        mockMvc.perform(get("/festival/edit/" + id.id()))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-festival"))
                .andExpect(model().attributeExists("festival"))
                .andExpect(model().attributeExists("festivalForm"))
                .andExpect(model().attributeExists("locations"));
    }

    @Test
    void updateFestival_mitFehlern_gibtFormularZurueck() throws Exception {
        // Vorbereitung: IDs und Location
        FestivalIdentifier id = new FestivalIdentifier(UUID.randomUUID());
        LocationIdentifier locId = new LocationIdentifier(UUID.randomUUID());
        Location loc = new Location(locId, "Test-Ort");

        // Existierendes Festival
        Festival fest = new Festival(
                "OldName",
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 2, 0, 0),
                loc
        );

        when(festivalRepo.findById(id)).thenReturn(Optional.of(fest));
        when(locations.findAll()).thenReturn(List.of(loc));

        // Fehlerhafter POST: Name leer → Validierungsfehler erwartet
        mockMvc.perform(
                        post("/festival/edit/error/" + id.id())
                                .param("name", "")
                                .param("startDate", "2024-01-01T00:00")
                                .param("endDate", "2024-01-02T00:00")
                                .param("locationId", locId.id().toString())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("edit-festival"));
    }

    @Test
    void deleteFestival_loeschtFestivalUndLeitetWeiter() throws Exception {
        // Vorbereitung: Festival-ID
        FestivalIdentifier id = new FestivalIdentifier(UUID.randomUUID());

        // POST zum Löschen
        mockMvc.perform(post("/festival/delete/" + id.id()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/festival"));

        verify(festivalRepo).deleteById(id);
    }
}
```

## Woche 7







###### `FestivalManagement.java`

```java
public class FestivalManagement {
//...
    // Festivals bearbeiten
 public void updateName(FestivalIdentifier id, FestivalForm form) {
        Festival festival = festivals.findById(id).orElseThrow();
        festival.setName(form.getName());
        festivals.save(festival);
    }

    public void updateStart(FestivalIdentifier id, FestivalForm form) {
        Festival festival = festivals.findById(id).orElseThrow();
        festival.setStartDate(form.getStartDate());
        festivals.save(festival);
    }

    public void updateEnd(FestivalIdentifier id, FestivalForm form) {
        Festival festival = festivals.findById(id).orElseThrow();
        festival.setEndDate(form.getEndDate());
        festivals.save(festival);
    }

    public void updateLocation(FestivalIdentifier id, FestivalForm form) {
        Festival festival = festivals.findById(id).orElseThrow();
        UUID uuid = UUID.fromString(form.getLocationId());
        LocationIdentifier locId = new LocationIdentifier(uuid);
        Location location = locationRepository.findById(locId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));
    }
}
```



##### 以 Start为例：

######  `FestivalController.java`

`@Controller`

```
   @PostMapping("/festival/{id}/updateStart")
    String updateStart(
            @PathVariable("id") FestivalIdentifier id,
            @Valid FestivalForm form,
            Errors result,
            Model model) {
        form.validate(result, festivalRepo);
        if (result.hasErrors()) {
            model.addAttribute("festival", festivalRepo.findById(id).orElse(null));
            return "festival";
        }
        try {
            festivalManagement.updateStartDate(id, form.getStartDate());
            return "redirect:/festival";
        } catch (Exception e) {
            model.addAttribute("festival", festivalRepo.findById(id).orElse(null));
            return "festival";
        }
    }
```



###### Front-end





## Woche 3

#### **Catering**

🟢 UC0011 Speisen abrechnen
 🟢 UC0010 Lagerbestände einsehen
 🟢 UC0013 Mitteilung an Festivalleitung
 🟢 UC0012 Vom Lagerbestand abziehen

------

#### **Datenvisualisierung**

UC0200 Betriebswirtschaftliche Daten visualisieren
 UC0201 Aktuelle Betriebslage grafisch darstellen
 UC0203 Besucherzahlen einsehen
 UC0204 Catering-Verkaufszahlen einsehen
 UC0202 Bühnenbelegung einsehen
 UC0201 Kostenaufteilung einsehen

------

#### **Bereichsplanung**

🔹 UC0080 Bereiche sperren
 🔹 UC0081 Bühnenpositionierung anpassen
 🔹 UC0101 Bühnen mieten
 🔹 UC0095 Toiletten mieten
 🔹 UC0021 Cateringstände platzieren
 🔹 UC0083 Toilettenverteilung anpassen
 🔹 UC0020 Cateringstände mieten

------

#### **Personalplanung**

🔹 UC0700 Künstlerangebote einholen
 🔹 UC0701 Künstler anwerben
 🔹 UC0702 Personal einstellen
 🔹 UC0710 Spielplan auf Bühne erstellen

------

#### **Verkauf / Ticketing**

🟢 UC0302 Kartenpreis festlegen
 🟢 UC0321 Karten an der Abendkasse verkaufen
 🟢 UC0300 Ticket auf Gültigkeit prüfen
 🟢 UC0301 Tickets verkaufen
 🟢 UC0311 Tickets drucken
 🟢 UC0312 Tickets vom Bestand abziehen
 🟢 UC0322 Karten in Filiale verkaufen

------

#### **Verwaltung**

###### 🔺 UC0100 Mitarbeiter-Logins verteilen
 🟢 UC0111 Nachbestellungen tätigen
 UC0120 Nachrichten senden und ansehen

------

#### **Festivalerstellung**

###### 🔺 UC0402 Location buchen

###### 🔺 UC0403 Festivalarbeiter zuordnen

###### 🔺 UC0400 Festival anlegen

###### 🔺 UC0411 Termin festlegen

###### 🔺 UC0410 Festivalplanung abschließen

###### 🔺 UC0412 Festival löschen

###### 🔺 **UC0420** Festival bearbeiten

------

#### **Terminals**

###### 🔺 UC0500 Festivalgeländeplan einsehen
###### 🔺 UC0501 Spielplan einsehen
###### 🔺 UC0520 Einloggen



### ✅ 中文

------

#### **Catering（餐饮）**

🟢 UC0011 结算餐饮
 🟢 UC0010 查看库存
 🟢 UC0013 向音乐节管理层发送通知
 🟢 UC0012 从库存中扣除

------

#### **Datenvisualisierung（数据可视化）**

（无颜色）
 UC0200 业务数据可视化
 UC0201 展示当前运营图形
 UC0203 查看当前访客数量
 UC0204 查看餐饮销售量
 UC0202 查看舞台占用情况
 UC0201 查看成本构成

------

#### **Bereichsplanung（区域规划）**

🔹 UC0080 封锁区域
 🔹 UC0081 调整舞台位置
 🔹 UC0101 租赁舞台
 🔹 UC0095 租赁厕所
 🔹 UC0021 放置餐饮摊位
 🔹 UC0083 调整厕所分布
 🔹 UC0020 租赁餐饮摊位

------

#### **Personalplanung（人员规划）**

🔹 UC0700 获取艺人报价
 🔹 UC0701 招募艺人
 🔹 UC0702 招聘工作人员
 🔹 UC0710 制定舞台演出计划

------

#### **Verkauf / Ticketing（票务）**

🟢 UC0302 设定票价
 🟢 UC0321 现场售票
 🟢 UC0300 检查门票有效性
 🟢 UC0301 销售门票
 🟢 UC0311 打印门票
 🟢 UC0312 从库存中扣除门票
 🟢 UC0322 在店铺售票

------

#### **Verwaltung（管理）**

🔺 UC0100 分配员工登录
 🟢 UC0111 进行追加订购
 UC0120 发送与查看消息

------

#### **Festivalerstellung（音乐节创建）**

🔺 UC0402 预订场地
 🔺 UC0403 分配音乐节工作人员
 🔺 UC0400 创建音乐节
 🔺 UC0411 设定日期
 🔺 UC0410 完成音乐节规划
 🔺 UC0412 删除音乐节

------

#### **Terminals（终端）**

🔺 UC0500 查看场地平面图
 🔺 UC0501 查看演出计划
 🔺 UC0520 登录
