package com.proiect.SCD.service;

import com.proiect.SCD.domain.ErrorModel;
import com.proiect.SCD.domain.Position;
import com.proiect.SCD.dto.DatePositionDto;
import com.proiect.SCD.dto.PositionDto;
import com.proiect.SCD.dto.UpdatePositionDto;
import com.proiect.SCD.exception.AppException;
import com.proiect.SCD.repository.PositionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PositionService {
   private PositionRepository positionRepository;

   public Position addPosition(PositionDto positionDto)
   {
        Position position = new Position();
        position.setLatitude(positionDto.getLatitude());
        position.setLongitude(positionDto.getLongitude());
        position.setTerminalId(positionDto.getTerminalId());
        return positionRepository.save(position);
   }

   public List<Position> getPositionsByDate(DatePositionDto datePositionDto){
      return positionRepository.getPositionByCreationDateBetweenAndTerminalId(datePositionDto.getStartDate(), datePositionDto.getEndDate(), datePositionDto.getTerminalId());
   }

   public List<Position> getAllPositions(){
       return positionRepository.findAll();
   }

   public ResponseEntity updatePosition(Integer positionId, UpdatePositionDto updatePositionDto){
           if (Float.parseFloat(updatePositionDto.getLatitude()) > 90 || Float.parseFloat(updatePositionDto.getLatitude()) < -90) {
               throw new AppException(HttpStatus.BAD_REQUEST, "Latitude should be a value between +90 and -90");
           }

           if (Float.parseFloat(updatePositionDto.getLongitude()) > 180 || Float.parseFloat(updatePositionDto.getLongitude()) < -190) {
               throw new AppException(HttpStatus.BAD_REQUEST, "Latitude should be a value between +180 and -190");
           }
           Position position = positionRepository.getById(positionId);
           position.setLongitude(updatePositionDto.getLongitude());
           position.setLatitude(updatePositionDto.getLatitude());
           positionRepository.save(position);
           return new ResponseEntity(HttpStatus.ACCEPTED);
   }

   public String deletePosition(Integer positionId){
       Position position = positionRepository.getById(positionId);
       positionRepository.delete(position);
       return "Position deleted";
   }
}
