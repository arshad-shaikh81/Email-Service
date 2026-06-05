//package com.emailservice.emailservice.model;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class EmailFormat {
//    private String toEmail;
//    private String name;
//    private String subject;
//    private String body;
//}
package com.emailservice.emailservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailFormat {
    private String toEmail;
    private String name;
    private String subject;
    private String body;
}
