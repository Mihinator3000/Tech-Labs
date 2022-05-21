package org.itmo.dto.rabbit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itmo.enums.Color;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColorDto {

    private Color color;

    private Integer ownerId;
}
