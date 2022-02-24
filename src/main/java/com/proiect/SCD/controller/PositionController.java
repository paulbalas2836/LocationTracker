package com.proiect.SCD.controller;

import com.proiect.SCD.dto.DatePositionDto;
import com.proiect.SCD.dto.PositionDto;
import com.proiect.SCD.dto.UpdatePositionDto;
import com.proiect.SCD.service.PositionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "/position")
@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:8100"})
public class PositionController {
    private PositionService positionService;


    @PostMapping(path = "/createPosition")
    public ResponseEntity createPosition(@Valid @RequestBody PositionDto positionDto){
        return new ResponseEntity(positionService.addPosition(positionDto), HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/getPositionsByDate")
    public ResponseEntity getPositionsByDate(@Valid @RequestBody DatePositionDto datePositionDto){
        return new ResponseEntity(positionService.getPositionsByDate(datePositionDto), HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "/updatePosition/{positionId}")
    public ResponseEntity updatePosition(@PathVariable Integer positionId, @Valid @RequestBody UpdatePositionDto updatePositionDto){
        return new ResponseEntity(positionService.updatePosition(positionId, updatePositionDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping(path = "/deletePosition/{positionId}")
    public ResponseEntity deletePosition(@PathVariable Integer positionId){
        return new ResponseEntity(positionService.deletePosition(positionId), HttpStatus.ACCEPTED);
    }
}
