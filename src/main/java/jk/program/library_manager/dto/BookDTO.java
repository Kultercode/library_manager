package jk.program.library_manager.dto;

import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class BookDTO {

    private Long id;
    @NotEmpty
    private String title;
    @NotNull
    private Date releaseDate;
    private Long writerId;
}
