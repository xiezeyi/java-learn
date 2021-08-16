import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.google.gson.JsonObject;
import springfox.documentation.spring.web.json.Json;

/**
 * @program: redis
 * @description:
 * @author: 谢泽毅
 * @create: 2021-08-16 16:59
 **/
public class test {
    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        obj.put("name", "tom");
        obj.put("age", 19);

        // 子对象
        JSONObject objContact = new JSONObject();
        objContact.put("tel", "123456");
        objContact.put("email", "tom@test.com");
        obj.put("contact", objContact);

        // 子数组对象
        JSONArray scoreArr = new JSONArray();
        JSONObject objEnglish = new JSONObject();
        objEnglish.put("course", "english");
        objEnglish.put("result", 100);
        objEnglish.put("level", "A");

        JSONObject objMath = new JSONObject();
        objMath.put("course", "math");
        objMath.put("result", 50);
        objMath.put("level", "D");

        scoreArr.add(objEnglish);
        scoreArr.add(objMath);

        obj.put("score", scoreArr);

        System.out.println(obj.toString());
        JSONArray score = obj.getJSONArray("score");
        for (int i = 0; i < score.size(); i++) {
            JSONObject o = (JSONObject) score.get(i);
            System.out.println("o = " + o);
            System.out.println("o2 = " + o.get("level"));
        }

    }
}
