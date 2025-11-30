![](./README.assets/image-20251126142244788.png)

## Woche 6

![](./README.assets/image.png)

---

`FestivalForm.java`

![](./README.assets/image-20251117085731584.png)

```java
 public void validate(Errors errors){
       //hier Check für Location einfügen
       if (startDate != null && endDate != null) {
          if (!startDate.isBefore(endDate)) {
             errors.rejectValue("endDate", "festival.time.invalid",
                "End date must be after the start date." );
          }
       }else{
          errors.rejectValue("endDate", "festival.time.invalid",
             "Please enter the date.");
       }

       if (locationId == null || locationId.isBlank()) {
          errors.rejectValue("locationId", "location.required",
             "Please select a location.");
       }

       if (startDate != null && endDate != null) {

          for (Festival existing : festivalRepo.findAll()) {

             FestivalForm location = new FestivalForm(
                existing.getName(),
                existing.getStartDate(),
                existing.getEndDate(),
                existing.getLocation().getLocationId()
             );
             
             if (!this.isNotConflicting(this, location)) {
                errors.rejectValue("startDate", "festival.conflict",
                   "The selected time conflicts with an existing festival at this location.");
                break;
             }
          }
       }
    }

//  public boolean isBefore(FestivalForm appointment) {
//     return appointment.getStartDate().isBefore(appointment.getEndDate());
//  } //Using `java.time`'s isBefore() directly. Custom method no longer needed.


    public boolean isNotConflicting(FestivalForm newFestival, FestivalForm location) {
       if (!Objects.equals(newFestival.getLocationId(), location.getLocationId())) {
          return false;
       }

       return newFestival.getEndDate().isBefore(location.getStartDate())
          ||newFestival.getStartDate().isAfter(location.getEndDate());
       // isBefore first used，and then
       //newFestival.getStartDate().isBefore(location.getStartDate()
       //return newFestival.getEndDate().isAfter(location.getEndDate()); are not necessary
    }
```



## Woche 5

![](./README.assets/image-20251111181443875.png)

Nach dem Klicken auf  `Anzeigen` wird unten eine Tabelle generiert und die Oberfläche aktualisiert![](README.assets/image-20251111181455981.png)
 In Zukunft wird diese Funktion mit anderen `festival`-Funktionen verknüpft, und der **Termin** kann im `festivalRepository` gespeichert werden.



`appointment.html`:

```html
<br>
<button
        type="submit"
        class="btn btn-primary"
        name="action"
        value="save">
    Anzeigen
    <!--            später: Termin speichern        -->
</button>
</form>
</section>

```



```

git commit -m "1.Datenmodell in FestivalForm  2.html konregieren"



```

```
        <table style="border-collapse: collapse;">
            <thead>
                <tr style="border-bottom: 2px solid #000;">
                    <th style="padding-right: 32px;">Name</th>
                    <th style="padding-right: 32px;">Beginndaten</th>
                    <th style="padding-right: 32px;">Enddaten</th>
                    <th style="padding-right: 32px;">Location</th>
                </tr>
            </thead>
            <tbody>
                <tr style="border-bottom: 1px solid #000;" th:each="festival : ${festivals}">
                    <td style="padding-right: 16px;" th:text="${festival.name}">Festival Name</td>
                    <td style="padding-right: 16px;"  th:text="${#temporals.format(festival.startDate, 'dd.MM.yyyy HH:mm')}"></td>
                    <td style="padding-right: 16px;" th:text="${#temporals.format(festival.startDate, 'dd.MM.yyyy HH:mm')}"></td>
                    <td style="padding-right: 16px;" th:text="${festival.location.name}">Location Name</td>
<!--                    <td>-->
<!--                        <button>Zeit ändern</button>-->
<!--                        <button>Location ändern</button>-->
<!--                        <button>löschen</button>-->
<!--                    </td>-->
```



```
<!--     ⬆️required wurde hinzugefügt, daher ist keine Fehlerprüfung mehr nötig.-->
<!--            <div th:if="${#fields.hasErrors('name')}" th:errors="*{name}">Name Error</div>-->
```
