package kickstart.festival;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;


public interface FestivalRepository extends CrudRepository<Festival, FestivalIdentifier> {
    @NotNull
	@Override
    Iterable<Festival> findAll();

    @Query("""
        SELECT f FROM Festival f
        WHERE f.endDate   >= :todayStart
          AND f.startDate <= :todayEnd
    """)
    Iterable<Festival> findToday(
            @Param("todayStart") LocalDateTime todayStart,
            @Param("todayEnd") LocalDateTime todayEnd
    );

    @Query("""
        SELECT f FROM Festival f
        WHERE f.startDate >= :todayStart
          AND f.endDate   <= :todayEnd
    """)
    Iterable<Festival> findOnlyToday(
            @Param("todayStart") LocalDateTime todayStart,
            @Param("todayEnd")   LocalDateTime todayEnd
    );

}

