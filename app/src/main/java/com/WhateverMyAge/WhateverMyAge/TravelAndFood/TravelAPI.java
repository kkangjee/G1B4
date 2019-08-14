package com.WhateverMyAge.WhateverMyAge.TravelAndFood;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

class APIdata {
    private String add1;
    private String add2;
    private String dist;
    private String title;
    private String image;
    private String cat1;
    private String cat2;
    private String cat3;

    public APIdata (String add1, String add2, String dist, String title, String image) {
        this.add1 = add1;
        this.add2 = add2;
        this.dist = dist;
        this.title = title;
        this.image = image;
        this.cat1=cat1;
        this.cat2=cat2;
        this.cat3=cat3;
    }

    String getAdd1() {
        return this.add1;
    }
    String getAdd2() {
        return this.add2;
    }
    String getDist() {
        return this.dist;
    }
    String getTitle() {
        return this.title;
    }
    String getImage() {
        return this.image;
    }
    String getCat2() { return this.cat2;}
    String getCat3() { return this.cat3;}
    String getCat1() { return this.cat1;}
    void setAdd1(String add1) {
        this.add1 = add1;
    }
    void setAdd2(String add2) {
        this.add2 = add2;
    }
    void setDist(String dist) {
        this.dist = dist;
    }
    void setTitle(String title) {
        this.title = title;
    }
    void setImage(String image) {
        this.image = image;
    }
    void setCat2(String cat2) {
        this.cat2 = cat2;
    }
    void setCat3(String cat3) {
        this.image = cat3;
    }
    void setCat1(String cat1) {
        this.image = cat1;
    }
}


public class TravelAPI {
    boolean FoodOrTravel = false;

    public TravelAPI(boolean FoodOrTravel) {
        this.FoodOrTravel = FoodOrTravel;
    }


    Bitmap bitmap;
    public static String baseURL;

    ArrayList<APIdata> getAPI (double lat, double lng) {

        StrictMode.enableDefaults();

        boolean initem = false, inaddr1 = false, inaddr2 = false, indist = false, intitle = false, inimage = false,incat2=false,
                incat3=false,incat1=false;;

        String addr1 = null, addr2 = null, dist = null, title = null, image = null,cat2=null, cat3=null,cat1=null;
        String key = "yOv5P9kxcP5VnWt8txP86aulztqNFQ1Tx848A4ZIyOgQCl0nnx6Zgp2iZO768lX2VyqpNqF8eXFYosPaxm6z%2FQ%3D%3D";

        double xpos = lat == 0 ? 126.9815706850 : lat;
        double ypos = lng == 0 ? 37.5685207373 : lng;

        ArrayList<APIdata> apiData=new ArrayList<>();

        try {
            int index = 0;
            Log.i("인덱스 초기화", " " + index);

            URL url = new URL("https://api.visitkorea.or.kr/openapi/service/rest/KorService/locationBasedList?ServiceKey="
                    + key + "&mapX=" + xpos + "&mapY=" + ypos + "&radius=1000&listYN=Y&arrange=B&MobileOS=ETC&MobileApp=AppTest"
            ); //검색 URL부분

            /*
             * arrange 를 A가 제목순 B가 조회순 E가 거리순
             * */

            Log.i("인덱스다음", "url");

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            Log.i("파스", "url");

            XmlPullParser parser = parserCreator.newPullParser();
            Log.i("파서", "url");

            parser.setInput(url.openStream(), null);
            Log.i("인풋", "url");


            int parserEvent = parser.getEventType();
            Log.i("파싱시작합니다.","");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("addr1")) { //addr1 만나면 내용을 받을수 있게 하자
                            inaddr1 = true;
                        }
                        if (parser.getName().equals("addr2")) { //addr2 만나면 내용을 받을수 있게 하자
                            inaddr2 = true;
                        }
                        if (parser.getName().equals("dist")) { //dist 만나면 내용을 받을수 있게 하자
                            indist = true;
                        }
                        if (parser.getName().equals("title")) { //title 만나면 내용을 받을수 있게 하자
                            intitle = true;
                        }
                        if (parser.getName().equals("firstimage")) {
                            inimage = true;
                        }
                        if (parser.getName().equals("cat2")) {
                            incat2 = true;
                        }

                        if (parser.getName().equals("cat3")) {
                            incat3 = true;
                        }
                        if (parser.getName().equals("cat1")) {
                            incat1 = true;
                        }



                        if (parser.getName().equals("message")) { //message 태그를 만나면 에러 출력
                            //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inaddr1) { //isaddr1이 true일 때 태그의 내용을 저장.
                            addr1 = parser.getText();
                            //apiData[index].setAdd1(parser.getText());
                            inaddr1 = false;
                        }
                        if (inaddr2) { //inaddr2이 true일 때 태그의 내용을 저장.
                            addr2 = parser.getText();
                            //apiData[index].setAdd2(parser.getText());
                            inaddr2 = false;
                        }
                        if (indist) { //indist이 true일 때 태그의 내용을 저장.
                            dist = parser.getText();
                            indist = false;
                        }
                        if (intitle) { //inMapx이 true일 때 태그의 내용을 저장.
                            title = parser.getText();
                            // apiData[index].APIdata(addr1, addr2, dist, title);
                            intitle = false;
                        }
                        if (inimage) {
                            image = parser.getText();
                            inimage = false;
                        }
                        if (incat2) {
                            cat2= parser.getText();
                            incat2 = false;
                        }
                        if (incat3) {
                            cat3 = parser.getText();
                            incat3 = false;
                        }
                        if (incat1) {
                            cat1 = parser.getText();
                            incat1 = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {

                            if (this.FoodOrTravel == false) { //여행지
                                if (cat1.equals("A05") || cat2.equals("A0208") || cat2.equals("A0207") || cat3.equals("A04010500") || cat3.equals("A04010600")) {
                                } else {

                                    Log.i("현재 인덱스", " " + index);
                                    Log.i("현재 정보", addr1 + " " + addr2 + " " + dist + " " + title);
                                    apiData.add(new APIdata(addr1, addr2, dist, title, image));

//                            status1.setText(status1.getText()+"지명 : "+ addr1 +"\n 주소: "+ addr2 +"\n 거리 : " + (dist*3) +"걸음" + "m\n 제목 : " + title
//                                    +"\n\n ");
//                            initem = false;
                                    index++;
                                }
                            }

                            else { //음식점
                                if (cat1.equals("A05")) {
                                    Log.i("현재 인덱스", " " + index);
                                    Log.i("현재 정보", addr1 + " " + addr2 + " " + dist + " " + title);
                                    apiData.add(new APIdata(addr1, addr2, dist, title, image));
//                            status1.setText(status1.getText()+"지명 : "+ addr1 +"\n 주소: "+ addr2 +"\n 거리 : " + (dist*3) +"걸음" + "m\n 제목 : " + title
//                                    +"\n\n ");
//                            initem = false;
                                    index++;
                                }
                            }
                            break;
                        }
                }
                parserEvent = parser.next();
            }

        } catch (Exception e) {
            Log.e("안 되지롱~", "");
        }
        return apiData;
    }
}