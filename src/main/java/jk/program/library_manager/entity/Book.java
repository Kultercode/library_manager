package jk.program.library_manager.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "release_date")
    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private Writer writer;
}
