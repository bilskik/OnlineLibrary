package pl.bilskik.lab34.data;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    @NotBlank(message = "Book name cannot be blank!")
    private String name;
    @NotBlank(message = "Author cannot be blank!")
    private String author;
}
