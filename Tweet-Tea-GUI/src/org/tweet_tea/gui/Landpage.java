package org.tweet_tea.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.Set;
import java.util.Stack;

import netscape.javascript.JSObject;

import org.tweet_tea.console.Console;
import org.tweet_tea.model.Bridge;
import org.tweet_tea.model.Tweet;
import org.tweet_tea.model.TwitterAPI;
import org.tweet_tea.model.User;
import org.tweet_tea.resources.Res;

import javafx.concurrent.Worker.State;;

/**
 * The GUI Landpage
 * @author Geoffrey
 *
 */
public class Landpage extends Application{

	// As you can see the component declaration is indented like the FXML file
	
	private Stage landpage;					// the window , "like" a JFrame
	
	@FXML private VBox mainVbox;			// The root visible container, a VBox 
	@FXML private  HBox menuBar;			// The menu bar, first element of the root pane
	@FXML private  	Button btnHome;			// The "home" button bound to the timeline
	@FXML private  	Button btnReduce;		// Used to reduce the window in the taskbar	
	@FXML private  	Button btnQuit;			// Used to Quit Tweet-Tea
	@FXML private 	Button btnTweet;		// Send a new Tweet
	@FXML private   Button btnMessage;		// Send a private message
	
	@FXML private 	TextField searchField;	// The search TextField
	@FXML private 	Button btnSearch;		// To start the search
	@FXML private  HBox newTweets;
	@FXML private  		Button btnNewTweets;
	@FXML private  HBox contentPane;		// To main content pane
	@FXML private  HBox footer;				// the footer
	@FXML private  	Button btnRefold;		// Used to reduce the window height .
	
	@FXML private ScrollBar contentScrollBar;
	
	//We use pop-ups to send messages and tweets
	private PopupPerso tweets_sender;
	private PopupPerso messages_sender;
	private User_Popup user_informations;
	
	private StackPane root;					// The root invisible container
	
	
	private double initialX;			// positions, Used to drag the window
	private double initialY;
	
	private String lastTweetID;
	
	//An array that contains previous tweets
	private Tweet [] refreshTweets;

	
	/**
	 * Size of the contentPane before refolding
	 */
	private double lastContentPaneHeight;

	/**
	 * Is the content pane displayed ? if we fold the window the content pane is hiden
	 */
	private boolean contentPaneDisplayed = true;
	
	
	/**
	 * Tweets:
	 */
	
	private TweetSectionGenerator tweetSection;		// allow to format the Tweets
	private WebView tweetView;						// the view in the content pane
	

	/**
	 * This method do (and must do) only ONE thing : call launch(args);
	 */
	public static void main(String[] args) {
		//We initialize the tweets array
		launch(args);
	}

	
	/**
	 * Is the equivalent of main in JavaFX ( notice this method isn't static)
	 */
	@Override
	public void start(final Stage landpage) throws Exception {
		
		this.landpage=landpage;		// wee keep this stage to acces him for an other method
		
		// We define the application's icon
		// using getClass().getResourceAsStream(""); is essential! it allow us to use relative paths
		Image icon = new Image( getClass().getResourceAsStream("/img/greenteaLeaf.png") );
		landpage.getIcons().add(icon);
		
		
		// we load the FXML.
		Parent fxml = FXMLLoader.load(getClass().getResource("/landpage.fxml"));
		
		// We create a window containing a first container like a JPanel in a JFrame , here StackPane is like the disposition management in JPanel
		root = new StackPane();
	
		root.setId("rootPane");		// like <StackPane fx:id="rootPane" /> in FXML
		
		Scene main = new Scene(root);   // we have the Scene : equivalent to JPanel 
		
		root.getChildren().add(fxml);	// we inject the FXML in the Stackpane.
		
		
				
		landpage.initStyle(StageStyle.UNDECORATED);			// we remove the window's borders
		landpage.initStyle(StageStyle.TRANSPARENT);			// we set the background to transparent
		main.setFill(Color.TRANSPARENT);					// idem
				
		landpage.setTitle("Tweet'Tea");						// title  //TODO: should be in Res
		landpage.setResizable(true);						// UseLess ? maybe , maybe not ... //TODO: discuss about that
        landpage.setScene(main);							// myJFrame.setContentPane(myJPanel);
        landpage.show();									// myJFrame.setVisible(true);
       
        //The window is ready, we can now get needed objects
        
        // myScene.lookup("") is like $("<css selector>") in jQuery or document.getElementById() in classic JavaScript.
        // beware to ClassCastException ...
        // Look at the indentation, it make the code easily understandable, like FXML
        
        mainVbox = (VBox) main.lookup("#mainVbox");
        	menuBar = (HBox) main.lookup("#menuBar");
        		btnHome = (Button) main.lookup("#btnHome");
        		btnReduce = (Button) main.lookup("#btnReduce");
        		
        		btnQuit = (Button) main.lookup("#btnQuit");
        		btnTweet = (Button) main.lookup("#btnTweet");
        		btnMessage = (Button) main.lookup("#btnMessage");
        		
        		searchField = (TextField) main.lookup("#searchField");
        		btnSearch = (Button) main.lookup("#btnSearch");
        		
        		newTweets = (HBox) main.lookup("#newTweets");
        		btnNewTweets = (Button) main.lookup("#btnNewTweets");
        		
        		//firstly this button is disabled
        		btnNewTweets.setDisable(true);
        		btnNewTweets.setOpacity(0.8);
        		
        	contentPane = (HBox) main.lookup("#contentPane");
        	
        	footer = (HBox) main.lookup("#footer");
        		btnRefold = (Button) main.lookup("#btnRefold");
        
   
        tweetSection = new TweetSectionGenerator();		// Allow us to dialog with JS
        
        tweetView = tweetSection.getWebView();			// Allow us to inject the WebView in the contentPane
        
        contentPane.getChildren().add(tweetView);		// and we do it
        
        	
        // We have our manipulable objects, now we set specifics dimensions. ( Why not in css ? css wasn't working when i have tried that...)
        
        contentPane.setPrefWidth(root.getWidth());
        landpage.setHeight(650);						// 650(px) is a good value because screens are commonly in 1366x768 or 1024x768 and the taskbar take ~40px
        		
        tweetView.setPrefWidth(contentPane.getWidth());
        
        //We bind objects and actions
        
        setBindings();				// set Bindings on buttons
        addDraggableNode(menuBar);	// we make the window drag by the menuBar

        
        // The GUI is ready, we can connect to twitter. The home timeline is called automatically 
          
        initializeTwitter();
        
        //We initalize the array when the javascript is ready
        tweetView.getEngine().getLoadWorker().stateProperty().addListener(
		        new ChangeListener<State>() {
		           
					public void changed(ObservableValue<? extends State> arg0,State arg1, State arg2) {
						// TODO Auto-generated method stub
						try {
							if(TwitterAPI.isConnected())
								refreshTweets = TwitterAPI.getHomeTimeline();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		});
        
        
        Timeline tl = new Timeline(new KeyFrame(Duration.seconds(Res.refreshTime), new EventHandler <ActionEvent>(){

			@Override
			public void handle(ActionEvent ae) {
				
				
				if(TwitterAPI.isConnected()){
					Tweet [] tweets = null;
					try {
						tweets = TwitterAPI.getHomeTimeline();
					} catch (Exception e) {
						// TODO make somthing better
						e.printStackTrace();
					}

					if(tweets[tweets.length - 1] != null){
						if(!tweets[tweets.length - 1].equals(refreshTweets[refreshTweets.length - 1])){
							btnNewTweets.setDisable(false);
							btnNewTweets.setOpacity(1);
						}
					}
				}
			}
        	
        }));
        
        //We set the cycle infinite
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();
        
        //A timeline to initialize the array of tweets
        /*Timeline t2 = new Timeline(new KeyFrame(Duration.seconds(Res.refreshTime - 10), new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					refreshTweets = TwitterAPI.getHomeTimeline();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
        	
        }));
        
        t2.play();*/
	}
	
	
	/**
	 * Acces to the home timeline at the startup
	 * Wait 1 sec before calling getHomeTimeline();
	 * @see getHomeTimeline(); 
	 */
	private void goToHomeTimeline(){

	// We use a JavaFX Thread here cause we need to let enough time to the webview to load. Else we get a JSException from webkit.
	// We launch the homepage after waiting 1 second
       Timeline timefromStart = new Timeline(new KeyFrame(Duration.seconds(0.5), new EventHandler<ActionEvent>() {

           @Override
           public void handle(ActionEvent event) {
               
               getHomeTimeline();
               
               Set<Node> nodes = tweetView.lookupAll(".scroll-bar");
               for (final Node node : nodes) {
                   if (node instanceof ScrollBar) {
                       ScrollBar sb = (ScrollBar) node;
                       if (sb.getOrientation() == Orientation.VERTICAL)
                       	contentScrollBar = sb;
                   }
               }
               
               if(contentScrollBar!=null)
               	contentScrollBar.valueProperty().addListener(new ChangeListener<Number>() {
       	            public void changed(ObservableValue<? extends Number> ov,
       	                Number old_val, Number new_val) {
       	                    if(contentScrollBar.getValue()>=contentScrollBar.getMax()*9/10)
       	                    {
       	
       	    					// we collect tweets 
       	    					Tweet[] tweets = null;
       	    					try {       	    						
       	    						tweets = TwitterAPI.getPage(lastTweetID);
       	    					} catch (Exception e) {
       	    						
       	    						goToAuthen();
       	    					}
       	    					
       	    					// if we have tweets 
       	    					if (tweets != null){
       	    						
       	    						for(Tweet t : tweets){
       	    							tweetSection.addTweet(t);
       	    						}
       	    						
       	    						lastTweetID = tweets[tweets.length-1].getID();
       	    						
       	    						tweetSection.showTweets();
       	    					}	
       	                    }
       	                }
       	        });
           }
       }));
       
       timefromStart.play();
		
	}
	
	/**
	 * Initialize TwitterApi
	 * If loadAuthToken throw a Excpetion we ask the user to authenticate
	 * @see Console Part
	 */
	private void initializeTwitter(){
		try {
			TwitterAPI.loadAuthToken();
			goToHomeTimeline();
			
		} catch (Exception e) {
			
			goToAuthen();
		}
		
	}
	
	/**
	 * Used to authenticate an User
	 * A webview is opened in a new Stage
	 * The opened Url comes from TwitterApi
	 * 
	 */
	
	private void goToAuthen(){
		
		String url = null;
		try {
			url = TwitterAPI.getFirstAuthURL();
		} catch (Exception e) {
			if(e.getMessage().equals("Already authentified")){
				return;
			}
		}
		
		final Stage Auth = new Stage();			// final is needed to use methods from a listener
		VBox container = new VBox();		
		Scene view = new Scene(container);
		Auth.setScene(view);
		final TextField pincode = new TextField();
		pincode.setPromptText("Enter here PinCode given by Twitter");
		Button ok = new Button("Ok");
		ok.setOnAction(new EventHandler<ActionEvent>() {		// we bind an action on ok.

			@Override
			public void handle(ActionEvent event) {
				String pin = pincode.getText();
				pincode.setText("");
				try {
					TwitterAPI.setPinCodefromTwitterAuth(pin);
					Auth.close();
					landpage.show();
					landpage.setHeight(650);
					goToHomeTimeline();
				}
				catch(Exception e){
					System.out.println("Wrong pincode :" + e.getMessage());
				}
				
			}
		});
		
		HBox hbox = new HBox();
		HBox.setHgrow(pincode, Priority.ALWAYS);
		hbox.getChildren().add(pincode);
		hbox.getChildren().add(ok);
		
		WebView website = new WebView();
		WebEngine engine = website.getEngine();
		engine.load(url);
		container.getChildren().add(hbox);
		container.getChildren().add(website);
		
		Auth.show();
		landpage.hide();
		
		
	}
	
	/**
	 * Bind events on buttons
	 */
	private void setBindings(){
//	        btnRefold.setOnAction(new EventHandler<ActionEvent>() {
//
//				@Override
//				public void handle(ActionEvent event) {
//					
//					if(contentPaneDisplayed){
//						lastContentPaneHeight = contentPane.getHeight();
//						Timeline hide = new Timeline();
//						hide.getKeyFrames().addAll(
//						        new KeyFrame(Duration.ZERO, // set start position at 0
//						           new KeyValue( contentPane.maxHeightProperty(), contentPane.getHeight())
//						            
//						            
//						        ),
//						        new KeyFrame(new Duration(100), // set end position at 100ms
//						        		new KeyValue( contentPane.maxHeightProperty(), 0 )
//						        		
//						          
//						        )
//						    );
//						
//						
//						
//						hide.play();
//						hide.setOnFinished(new EventHandler<ActionEvent>() {
//
//							@Override
//							public void handle(ActionEvent event) {
//								
//								mainVbox.getChildren().remove(contentPane);
//							}
//						});
//						
//						btnRefold.setText(">");
//						contentPaneDisplayed = false;
//					}
//					else{
//						Timeline show = new Timeline();
//						show.getKeyFrames().addAll(
//						        new KeyFrame(Duration.ZERO, // set start position at 0
//						            new KeyValue( contentPane.maxHeightProperty(), 0)
//						           
//						            
//						        ),
//						        new KeyFrame(new Duration(100), // set end position at 400ms
//						        		new KeyValue( contentPane.maxHeightProperty(), lastContentPaneHeight)
//						        		
//						          
//						        )
//						    );
//						
//						mainVbox.getChildren().add(2, contentPane);
//						show.play();
//						show.setOnFinished(new EventHandler<ActionEvent>() {
//
//							@Override
//							public void handle(ActionEvent event) {
//							
//								contentPane.setMaxHeight(Double.MAX_VALUE);
//							}
//						});
//						btnRefold.setText("<");
//						contentPaneDisplayed = true;
//					}
//				}
//	        	
//			});
	        
	        btnHome.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					getHomeTimeline();	
					
				}
			});
	        
	        btnQuit.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					quit();
				}
			});
	        btnReduce.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					landpage.setIconified(true);
				}
			});
	        btnNewTweets.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent arg0) {
					// TODO Auto-generated method stub
					getHomeTimeline();
					
					//We set the button disabled
					btnNewTweets.setDisable(true);
					btnNewTweets.setOpacity(0.8);
					
					//We save tweets
					try {
						refreshTweets = TwitterAPI.getHomeTimeline();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	        	
	        });
	        	        
	        
	        /**
	         * Research
	         */
	        
	        searchField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){

				@Override
				public void handle(KeyEvent event) {
										
					if(event.getCode() == KeyCode.ENTER){
						btnSearch.fire();
					}
					
				}
	        	
	        });	        
	        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent arg0) {
				
					String query = searchField.getText();
					if(query.isEmpty()){
						searchField.setPromptText("Enter your search here ... caledonianNews or GCU or what you want...");
						return;
					}
					
					if(!contentPaneDisplayed){
						btnRefold.fire();		// simulate click
					}
					
					// We collect tweets
					Tweet[] tweets = null;
					try {
						tweets = TwitterAPI.search(query);
					} catch (Exception e) {
						
						System.out.println("Search : error : " + e.getMessage());
					}
					
					// if we have tweets
					if (tweets != null){

						// we clean
						tweetSection.clear();
						
						// we add tweets
						for(Tweet t : tweets){
							tweetSection.addTweet(t);
						}
						tweetSection.showTweets();
					}
					
					
				
				}
			});
	        
	        /**
	         * Send a Tweet
	         */
	        btnTweet.setOnAction(new EventHandler<ActionEvent>(){
	        	@Override
	        	public void handle(ActionEvent ae){
	        		try{
	        			//We create a pop-up. All is done in this object
	        			tweets_sender = new PopupPerso("/tweet_popup.fxml");
	        		}catch(Exception e){
	        			//TODO: make somthing better GG-FR
	        			System.out.println(e);
	        		}
	        		
	        	}
	        });
	        
	        btnMessage.setOnAction(new EventHandler<ActionEvent>(){
	        	@Override
	        	public void handle(ActionEvent ae){
	        		try{
	        			//We create a pop-up.
	        			messages_sender = new PopupPerso("/privateMess_popup.fxml");
	        		}catch(Exception e){
	        			//TODO: make somthing better GG-FR
	        			System.out.println(e);
	        		}
	        	}
	        });
	        
	}
	
	/**
	 * Makes  elements draggable 
	 * @param node a Node which is the SuperClass of Component
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
	
	
	/**
	 * Get the user tweets and folower's tweets
	 */
	private void getHomeTimeline(){
		
		// we collect tweets 
		Tweet[] tweets = null;
		try {
			tweets = TwitterAPI.getHomeTimeline();
			refreshTweets = tweets;
		} catch (Exception e) {
			
			goToAuthen();
		}
		
		// if we have tweets 
		if (tweets != null){
			final Tweet[] tweetsCopy = tweets;
//			final WebEngine engine = tweetView.getEngine();
//			engine.getLoadWorker().stateProperty().addListener(
//			        new ChangeListener<State>() { 
//						public void changed(ObservableValue<? extends State> arg0,
//								State arg1, State newState) {
//							// TODO Auto-generated method stub
//							if ( newState == State.SUCCEEDED){
//								engine.getLoadWorker().stateProperty().removeListener(this);
								tweetSection.clear();
								for(Tweet t : tweetsCopy){
									tweetSection.addTweet(t);
								}
								
								lastTweetID = tweetsCopy[tweetsCopy.length-1].getID();
								
								tweetSection.showTweets();
//							}
//						}
//			        });
			
			
			
		}		
	}
	
	
	/**
	 * Quit the app
	 */
	
	protected void quit(){
		Platform.exit();
	}
	
	public void addUserpopup(User_Popup up){
		user_informations = up;
	}
}
