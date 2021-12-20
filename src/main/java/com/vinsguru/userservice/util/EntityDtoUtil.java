package com.vinsguru.userservice.util;

import com.vinsguru.userservice.dto.TransactionRequestDto;
import com.vinsguru.userservice.dto.TransactionResponseDto;
import com.vinsguru.userservice.dto.TransactionStatus;
import com.vinsguru.userservice.dto.UserDto;
import com.vinsguru.userservice.entity.User;
import com.vinsguru.userservice.entity.UserTransaction;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@UtilityClass
public class EntityDtoUtil {

    public User toEntity(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        return user;
    }

    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        return userDto;
    }

    public UserTransaction toEntity(TransactionRequestDto transactionRequestDto) {
        return UserTransaction.builder()
                .userId(transactionRequestDto.getUserId())
                .amount(transactionRequestDto.getAmount())
                .transactionDate(LocalDateTime.now())
                .build();
    }

    public TransactionResponseDto toDto(TransactionRequestDto requestDto, TransactionStatus status) {
        return TransactionResponseDto.builder()
                .userId(requestDto.getUserId())
                .amount(requestDto.getAmount())
                .status(status)
                .build();
    }

}
