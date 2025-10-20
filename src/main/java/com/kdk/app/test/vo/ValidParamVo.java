package com.kdk.app.test.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * -----------------------------------
 * 개정이력
 * -----------------------------------
 * 2025. 10. 20. 김대광	최초작성
 * </pre>
 *
 *
 * @author 김대광
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValidParamVo {

	@NotBlank(message = "사이즈는 필수 항목입니다.")
	@Size(min = 4, max = 4, message = "사이즈는 4자리입니다.")
	private String size;

	@NotBlank(message = "번호는 필수 항목입니다.")
	@Pattern(regexp = "\\d+", message = "번호는 숫자 형태여야 합니다.")
	private String number;

	@NotBlank(message = "일자는 필수 항목입니다.")
	@Pattern(regexp = "^\\d{8}$", message = "일자는 YYYYMMDD 형식이어야 합니다.")
	private String date;

	@NotBlank(message = "시간은 필수 항목입니다.")
	@Pattern(regexp = "^([01]\\d|2[0-3])[0-5]\\d$", message = "시간은 HHmm 형식이어야 합니다.")
	private String time;

	@NotBlank(message = "여부는 필수 항목입니다.")
	@Pattern(regexp = "^[YN]$", message = "여부는 Y 또는 N이어야 합니다.")
	private String yn;

	@NotBlank(message = "전화번호는 필수 항목입니다.")
	@Pattern(regexp = "^(02|03[1-3]|04[1-4]|05[1-5]|06[1-4])-?(\\d{3,4})-?(\\d{4})|^(070|050[2-7])-?(\\d{4})-?(\\d{4})|^(15|16|18)\\d{2}-?(\\d{4})$", message = "전화번호 형식이 올바르지 않습니다.")
	private String phone;

	@NotBlank(message = "휴대전화번호는 필수 항목입니다.")
	@Pattern(regexp = "^010-?\\d{4}-?\\d{4}$", message = "휴대전화번호 형식이 올바르지 않습니다.")
	private String cellPhone;

	@NotBlank(message = "이메일은 필수 항목입니다.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "이메일 형식이 올바르지 않습니다.")
	private String email;

}
