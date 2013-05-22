package org.tweet_tea.gui;

import org.tweet_tea.model.TwitterAPI;
import org.tweet_tea.model.User;
import org.tweet_tea.resources.Res;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	private 			ImageView background;
	private 			ImageView avatar;
	@FXML private 	HBox userNameBox;
	@FXML private 		Text username;
	@FXML private 		TextField changeUsername;
	@FXML private 	HBox screenNameBox;
	@FXML private 		Text screen_name;
	@FXML private	HBox descriptionBox;
	@FXML private		Text description;
	@FXML private		TextArea changeDescription;
	@FXML private 	HBox twitterInformations; //Informations about the twitter account : number of tweets, followers, followed, and buttons to block or follow
	@FXML private		Text nbTweets;
	@FXML private 		Text nbFollowers;
	@FXML private		Text nbFollowed;
	@FXML private 		Button btnFollow; //Button to follow the user
	@FXML private 		Button btnBlock; //Button to block the user
	@FXML private		Button btnCancel;
	@FXML private		Button btnModify;
	@FXML private 	HBox footer;
	
	private double initialX;
	private double initialY;
	
	//Builder
	public User_Popup(final User user)throws Exception{
		
		//We verify if the user is the authenticated user
		User me = TwitterAPI.getMyUserInfo();
		
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
		
		mainVBox = (VBox) root.lookup("#mainVbox");
			header = (HBox) root.lookup("#menuBar");
				title = (Text) root.lookup("#title");
			avatarPlace = (HBox) root.lookup("#avatarPlace");
			informations = (HBox) root.lookup("#contentPane");
				userNameBox = (HBox) root.lookup("#userNameBox");
					username = (Text) root.lookup("#user_name");
					changeUsername = (TextField) root.lookup("#changeUsername");
				screenNameBox = (HBox) root.lookup("#screenNameBox");
					screen_name = (Text) root.lookup("#screen_name");
				descriptionBox = (HBox) root.lookup("#descriptionBox");
					description = (Text) root.lookup("#description");
					changeDescription = (TextArea) root.lookup("#changeDescription");
				nbTweets = (Text) root.lookup("#nbTweets");
				nbFollowers = (Text) root.lookup("#nbFollowers");
				nbFollowed = (Text) root.lookup("#nbFollowed");
			footer = (HBox) root.lookup("#footer");
					btnFollow = (Button) root.lookup("#btnFollow");
					btnBlock = (Button) root.lookup("#btnBlock");
					btnModify = (Button) root.lookup("#btnModify");
				btnCancel = (Button) root.lookup("#btnCancel");
				
		//We set the size of HBoxs
		HBox.setMargin(userNameBox, new Insets(20));
		HBox.setMargin(screenNameBox, new Insets(20));
		HBox.setMargin(description, new Insets(20));
		HBox.setMargin(informations, new Insets(10));
				
		//popup.setHeight(800);
		
		//for(int i =0; i<mainVBox.getChildren().size(); i++) mainVBox.getChildren().remove(i)
		
		title.setFont(new Font(20));
		//mainVBox.getChildren().add(0, header);
		
		avatar = new ImageView(user.getImageURL().replace("normal", "bigger"));
		avatar.setLayoutX(50);
		avatar.setLayoutY(50);
		Rectangle r = new Rectangle(73, 73);
		r.setArcHeight(10);
		r.setArcWidth(10);
		avatar.setClip(r);
		
		avatarPlace.getChildren().add(avatar);
		
		
		//mainVBox.getChildren().add(2, informations);
		//We set the text of informations
		userNameBox.getChildren().remove(changeUsername);
		username.setText(user.getName()+"\n");
		username.setFont(new Font(17));
		//username.setEditable(false);
		screen_name.setText("@"+user.getScreenName()+"\n");
		screen_name.setFont(new Font(17));
		//informations.getChildren().add(username);
		
		btnModify.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				TwitterAPI.changeStatus(changeUsername.getText(), changeDescription.getText());
				User u = TwitterAPI.getMyUserInfo();
				username.setText(u.getName());
				description.setText(u.getDescription());
				btnModify.setDisable(false);
				userNameBox.getChildren().remove(0);
				userNameBox.getChildren().add(username);
				descriptionBox.getChildren().remove(0);
				descriptionBox.getChildren().add(description);
			}
			
		});
		footer.getChildren().remove(btnModify);
		descriptionBox.getChildren().remove(changeDescription);
		
		//mainVBox.getChildren().add(2, informations);
		/*userNameBox.getChildren().remove(0);
		userNameBox.getChildren().add(username);
		screenNameBox.getChildren().remove(0);
		screenNameBox.getChildren().add(screen_name);
		descriptionBox.getChildren().remove(0);
		descriptionBox.getChildren().add(description);*/
		
		/*informations.getChildren().remove(0);
		informations.getChildren().add(userNameBox);
		informations.getChildren().add(screenNameBox);
		informations.getChildren().add(description);*/
		
		description.setText(user.getDescription());
		description.setFont(new Font(14));
		description.setWrappingWidth(300);
		//descriptionBox.getChildren().add(description);
		
		//nbTweets.setText("Tweets : "+user.getNbTweets());
		nbFollowers.setText("Followers : "+user.getFollowersCount()+"  ");
		nbFollowed.setText("Followed : "+user.getFollowedCount());
		
		if(!me.getName().equals(user.getName()))
			title.setText("User's information");
		else{
			footer.getChildren().remove(btnFollow);
			footer.getChildren().remove(btnBlock);
			footer.getChildren().add(0, btnModify);
			btnModify.setDisable(true);
			title.setText("My information");
			username.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					userNameBox.getChildren().remove(username);
					userNameBox.getChildren().add(changeUsername);
					changeUsername.setText(username.getText());
					btnModify.setDisable(false);
				}
			});
			description.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

				@Override
				public void handle(MouseEvent arg0) {
					descriptionBox.getChildren().remove(0);
					descriptionBox.getChildren().add(changeDescription);
					changeDescription.setText(description.getText());
					changeDescription.setMinWidth(300);
					changeDescription.setMinHeight(100);
					changeDescription.setMaxHeight(100);
					changeDescription.setMaxWidth(400);
					btnModify.setDisable(false);
				}
				
			});
		}
		
		if(user.isFollowed())
			btnFollow.setText("Unfollow");
		
		if(!me.getName().equals(user.getName())){
			btnFollow.setOnAction(new EventHandler<ActionEvent>(){
				
				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					try{
						if(!user.isFollowed()){
							TwitterAPI.follow(user.getScreenName());
							btnFollow.setText("Unfollow");
							user.setFollowed(true);
						}else{
							TwitterAPI.unfollow(user.getScreenName());
							btnFollow.setText("Follow");
							user.setFollowed(false);
						}
						//We change the function of the button
						/*btnFollow.setOnAction(new EventHandler<ActionEvent>(){
							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub
								try{
									TwitterAPI.unfollow(user.getScreenName());
									btnFollow.setText("Follow");
									user.setFollowed(false);
								}catch(Exception e){
									System.out.println(e.getMessage());
								}
							}

						});*/
					}catch(Exception e){
						System.out.print(e.getMessage());
					}
				}
			});
		/*}else{
			//In the other case, we change the function of the button
			btnFollow.setText("Unfollow");
			btnFollow.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					try{
						TwitterAPI.unfollow(user.getScreenName());
						btnFollow.setText("Follow");
						user.setFollowed(false);
						
						//We change the function associated to the button
						btnFollow.setOnAction(new EventHandler<ActionEvent>(){

							@Override
							public void handle(ActionEvent arg0) {
								// TODO Auto-generated method stub
								try{
									TwitterAPI.follow(user.getScreenName());
									btnFollow.setText("Unfollow");
									user.setFollowed(true);
								}catch(Exception e){
									System.out.print(e.getMessage());
								}
							}
						});
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
				}
				
			});
		}*/
		
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
		
		btnCancel.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				popup.close();
			}
			
		});
		
		//We change the height of the window
		/*System.out.println(header.getHeight());
		System.out.println(avatarPlace.getHeight());
		System.out.println(informations.getHeight());
		System.out.println(footer.getHeight());*/
		description.maxHeight(300);

		System.out.println(header.getHeight() + avatarPlace.getHeight() + informations.getHeight() + footer.getHeight());

		//popup.setHeight(header.getHeight() + avatarPlace.getHeight() + informations.getHeight() + footer.getHeight());
		popup.setWidth(500);
		popup.setHeight(400);
		
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
	    description.setTextAlignment(TextAlignment.CENTER);
	    
	    EventHandler hand = new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				
			}
	    	
	    };
	    
	    popup.addEventHandler(MouseEvent.MOUSE_CLICKED, hand); 
	    
	    userNameBox.removeEventHandler(MouseEvent.MOUSE_CLICKED, hand);
	    descriptionBox.removeEventHandler(MouseEvent.MOUSE_CLICKED, hand);
	}
	
	public void disableModify(){
		btnModify.setDisable(true);
		descriptionBox.getChildren().remove(0);
		descriptionBox.getChildren().add(description);
		userNameBox.getChildren().remove(0);
		userNameBox.getChildren().add(username);
	}
}