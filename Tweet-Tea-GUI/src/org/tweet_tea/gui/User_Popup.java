package org.tweet_tea.gui;

import org.tweet_tea.model.TwitterAPI;
import org.tweet_tea.model.User;
import org.tweet_tea.resources.Res;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
	@FXML private	HBox avatarPlace;
	private 			ImageView avatar;
	@FXML private 	HBox userNameBox;
	@FXML private 		Text username;
	@FXML private 	HBox screenNameBox;
	@FXML private 		Text screen_name;
	@FXML private	HBox descriptionBox;
	@FXML private		Text description;
	@FXML private 	HBox twitterInformations; //Informations about the twitter account : number of tweets, followers, followed, and buttons to block or follow
	@FXML private		Text nbTweets;
	@FXML private 		Text nbFollowers;
	@FXML private		Text nbFollowed;
	@FXML private 		Button btnFollow; //Button to follow the user
	@FXML private 		Button btnBlock; //Button to block the user
	@FXML private		Button btnCancel;
	@FXML private 	HBox footer;
	
	private double initialX;
	private double initialY;
	
	//Builder
	public User_Popup(final User user)throws Exception{
		popup = new Stage();
		if(user == null) System.out.println("NULL");
		
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
		
		mainVBox = (VBox) root.lookup("#mainVbox");
			header = (HBox) root.lookup("#menuBar");
				title = (Text) root.lookup("#title");
			avatarPlace = (HBox) root.lookup("#avatarPlace");
			informations = (HBox) root.lookup("#contentPane");
				userNameBox = (HBox) root.lookup("#userNameBox");
					username = (Text) root.lookup("#user_name");
				screenNameBox = (HBox) root.lookup("#screenNameBox");
					screen_name = (Text) root.lookup("#screen_name");
				descriptionBox = (HBox) root.lookup("#descriptionBox");
					description = (Text) root.lookup("#description");
				nbTweets = (Text) root.lookup("#nbTweets");
				nbFollowers = (Text) root.lookup("#nbFollowers");
				nbFollowed = (Text) root.lookup("#nbFollowed");
			footer = (HBox) root.lookup("#footer");
				btnFollow = (Button) root.lookup("#btnFollow");
				btnBlock = (Button) root.lookup("#btnBlock");
				btnCancel = (Button) root.lookup("#btnCancel");
				
		popup.setHeight(800);
		popup.setWidth(500);
						
		avatar = new ImageView(user.getImageURL());
		avatar.setLayoutX(50);
		avatar.setLayoutY(50);
		Rectangle r = new Rectangle(50, 50);
		r.setArcHeight(10);
		r.setArcWidth(10);
		avatar.setClip(r);
		
		avatarPlace.getChildren().add(avatar);
		
				
		title.setText("User's information");
		title.setFont(new Font(20));
		
		//We set the text of informations
		username.setText(user.getName()+"\n");
		username.setFont(new Font(17));
		screen_name.setText("@"+user.getScreenName()+"\n");
		screen_name.setFont(new Font(17));
		description.setText(user.getDescription());
		description.setFont(new Font(14));
		description.setWrappingWidth(300);
		
		//nbTweets.setText("Tweets : "+user.getNbTweets());
		nbFollowers.setText("Followers : "+user.getFollowersCount());
		nbFollowed.setText("Friends : "+user.getFollowedCount());
			
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
		
		btnCancel.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				popup.close();
			}
			
		});
		
		addDraggableNode(mainVBox);
	}
	
	/**
	 * Same as in Landpage.java
	 * @param node a Node
	 */
	private void addDraggableNode(final Node node) {

	    node.setOnMousePressed(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	            if (me.getButton() != MouseButton.MIDDLE) {
	                initialX = me.getSceneX();
	                initialY = me.getSceneY();
	            }
	        }
	    });

	    node.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent me) {
	            if (me.getButton() != MouseButton.MIDDLE) {
	                node.getScene().getWindow().setX(me.getScreenX() - initialX);
	                node.getScene().getWindow().setY(me.getScreenY() - initialY);
	            }
	        }
	    });
	}
}