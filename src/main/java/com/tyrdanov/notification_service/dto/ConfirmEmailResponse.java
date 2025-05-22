package com.tyrdanov.notification_service.dto;

import lombok.Data;

@Data
public class ConfirmEmailResponse {
    
    Long userId;

    Boolean isConfirmed;

}
