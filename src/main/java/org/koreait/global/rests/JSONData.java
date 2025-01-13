package org.koreait.global.rests;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@NoArgsConstructor
public class JSONData { /*예측 가능한 형태로 하기 위해*/
    private HttpStatus status = HttpStatus.OK;
    private boolean success = true;
    private Object message; // 실패시 에러 메세지
    private Object data; // 성공시 데이터

    public JSONData(Object data){
        this.data = data;
    }
}
