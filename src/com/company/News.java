package com.company;

public class News {
    String headLine = "Head Line";
    String imgSource = "https://addisfortune.net/wp-content/themes/smdthemeaddis/thumbnails/editorspick-default-tnail.png";
    String description = "No description provided";
    String newsLink;

    String newBuildingString = "<section><div class=\"imgcontainer\"><img src=\"imgsrc\"/></div><div class=\"descriptors\"><h3>headline</h3><p>description</p><a href=\"link\">Read More</a></div></section>";

    public News(String headLine, String newsLink, String description, String imgSource){
        this.headLine = headLine;
        this.newsLink = newsLink;
        this.description = description;
        this.imgSource = imgSource;
    }

    public News(String headLine, String newsLink, String description){
        this.headLine = headLine;
        this.newsLink = newsLink;
        this.description = description;
    }

    public News(String headLine, String newsLink){
        this.headLine = headLine;
        this.newsLink = newsLink;
    }

    public String createNews(){

        String modifiedString = newBuildingString.replace("imgsrc", this.imgSource).replace("headline",this.headLine);
        modifiedString = modifiedString.replace("description",this.description).replace("link",this.newsLink);

        return modifiedString;
    }
}
