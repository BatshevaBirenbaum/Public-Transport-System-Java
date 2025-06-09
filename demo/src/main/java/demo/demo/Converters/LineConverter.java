package demo.demo.Converters;

import java.util.List;
import java.util.stream.Collectors;

import demo.demo.DTO.LineDTO;
import demo.demo.Models.Line;

public class LineConverter {
    public static LineDTO toDTO(Line line) {
        LineDTO lineDTO = new LineDTO();
        lineDTO.setId(line.getId());
        lineDTO.setNumber(line.getNumber());
        lineDTO.setSource(line.getSource());
        lineDTO.setDestination(line.getDestination());

        return lineDTO;

    }

    public static Line toModel(LineDTO lineDTO) {
        Line line = new Line();
        line.setId(lineDTO.getId());
        line.setNumber(lineDTO.getNumber());
        line.setSource(lineDTO.getSource());
        line.setDestination(lineDTO.getDestination());

        return line;
    }

    public static List<LineDTO> toListDto(List<Line> line) {
        return line.stream().map(b -> toDTO(b)).collect(Collectors.toList());
    }

    public static List<Line> toListModel(List<LineDTO> lineDto) {
        return lineDto.stream().map(b -> toModel(b)).collect(Collectors.toList());
    }
}
