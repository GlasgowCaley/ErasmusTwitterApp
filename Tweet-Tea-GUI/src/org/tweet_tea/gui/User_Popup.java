package org.tweet_tea.gui;


import org.tweet_tea.model.TwitterAPI;
import org.tweet_tea.model.User;
import org.tweet_tea.resources.Res;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * This class is the popup to display user's inforations, and to block or follow him
 * @author Vincent
 *
 */
public class User_Popup{
	//Attributes
	private Stage popup;
	private Scene scene;
	private StackPane root;
	
	@FXML private VBox mainVBox;
	@FXML private 	HBox header;
	@FXML private 		Text title;
	@FXML private 	HBox informations; //Informaitions about the user : image, name, screen name, ...
	@FXML private 		Text username;
	@FXML private 		Text screen_name;
	@FXML private 	HBox twitterInformations; //Informations about the twitter account : number of tweets, followers, followed, and buttons to block or follow
	@FXML private 		Button btnFollow; //Button to follow the user
	@FXML private 		Button btnBlock; //Button to block the user
	@FXML private 	HBox footer;
	
	//Builder
	public User_Popup(final User user)throws Exception{
		popup = new Stage();
		
		Parent fxml = FXMLLoader.load(getClass().getResource("/User_Popup.fxml"));
		
		root = new StackPane();
		root.setId("root");
		
		scene = new Scene(root);
		
		root.getChildren().add(fxml);
		
		//We display the popup
		popup.initStyle(StageStyle.UNDECORATED);
		popup.initStyle(StageStyle.TRANSPARENT);
		scene.setFill(Color.TRANSPARENT);
		popup.setResizable(true);
		popup.setScene(scene);
		popup.show();
		
		mainVBox = (VBox) root.lookup("#mainVBox");
			header = (HBox) root.lookup("#header");
				title = (Text) root.lookup("#title");
			informations = (HBox) root.lookup("#informations");
				username = (Text) root.lookup("#user_name");
				screen_name = (Text) root.lookup("#screen_name");
			twitterInformations = (HBox) root.lookup("#twitterInformations");
				btnFollow = (Button) root.lookup("#follow");
				btnBlock = (Button) root.lookup("#block");
			footer = (HBox) root.lookup("#footer");
			
		//We set the text of informations
		username.setText(user.getName());
		screen_name.setText(user.getScreenName());
			
		btnFollow.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try{
					TwitterAPI.follow(user.getScreenName());
				}catch(Exception e){
					System.out.print(e.getMessage());
				}
			}
		});
		
		btnBlock.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try{
					TwitterAPI.blockUser(user.getScreenName());
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
			
		});
	}
}