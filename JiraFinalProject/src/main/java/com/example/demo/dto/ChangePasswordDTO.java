package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ChangePasswordDTO {
	private String oldPassword;
	private String newPassword;
	private String newPasswordConfirm;
}
