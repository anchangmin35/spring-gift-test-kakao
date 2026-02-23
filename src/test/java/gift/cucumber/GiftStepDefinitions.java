package gift.cucumber;

import gift.model.OptionRepository;
import io.cucumber.java.ko.그리고;
import io.cucumber.java.ko.만약;
import io.cucumber.java.ko.조건;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GiftStepDefinitions {

    @Autowired
    private SharedContext sharedContext;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OptionRepository optionRepository;

    @조건("보내는 회원과 받는 회원이 존재한다")
    public void 보내는_회원과_받는_회원이_존재한다() {
        jdbcTemplate.update("INSERT INTO member (id, name, email) VALUES (1, '보내는사람', 'sender@test.com')");
        jdbcTemplate.update("INSERT INTO member (id, name, email) VALUES (2, '받는사람', 'receiver@test.com')");
    }

    @그리고("카테고리 {string}와 상품 {string}과 수량이 {int}인 옵션 {string}이 존재한다")
    public void 카테고리와_상품과_옵션이_존재한다(String categoryName, String productName, int quantity, String optionName) {
        jdbcTemplate.update("INSERT INTO category (id, name) VALUES (1, ?)", categoryName);
        jdbcTemplate.update("INSERT INTO product (id, name, price, image_url, category_id) VALUES (1, ?, 10000, 'http://image.url', 1)", productName);
        jdbcTemplate.update("INSERT INTO \"OPTION\" (id, name, quantity, product_id) VALUES (1, ?, ?, 1)", optionName, quantity);
    }

    @만약("회원 {long}이 옵션 {long}을 수량 {int}으로 회원 {long}에게 선물한다")
    public void 회원이_옵션을_수량으로_회원에게_선물한다(long senderId, long optionId, int quantity, long receiverId) {
        var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Member-Id", senderId)
                .body(Map.of(
                        "optionId", optionId,
                        "quantity", quantity,
                        "receiverId", receiverId,
                        "message", "선물입니다"
                ))
                .when()
                .post("/api/gifts");
        sharedContext.setResponse(response);
    }

    @그리고("옵션이 존재하지 않는다")
    public void 옵션이_존재하지_않는다() {
        assertThat(optionRepository.findAll()).isEmpty();
    }

    @그리고("옵션 {long}의 수량은 {int}이다")
    public void 옵션의_수량은_이다(long optionId, int expectedQuantity) {
        var option = optionRepository.findById(optionId).orElseThrow();
        assertThat(option.getQuantity()).isEqualTo(expectedQuantity);
    }
}
