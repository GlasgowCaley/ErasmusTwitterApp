/*
###########################################################################################

Warning! We have here a particular version of WebKit and there are some particularities:
	- Only /* *\/ comments are allowed -> if you use // -> JSException at startup
	- You NEED to run the js manualy at startup , else, variables & function stay 'undefined'
	- We are here on a bad JS platform, we need to act like a programmer in the 70's : save memory & process time ^^ 

###########################################################################################
*/

/*	Globals */

var twitterPrefix="https://twitter.com/";

/*
	An array of tweets... can be usefull... ( sorting  or other...)
*/
var tabTweet = [];



/*________________________*/


/* initialize the webview by showing a prompt text and make JS working correctly */
function initialize(){


	$("#tweetSection").append("<div id='promptStartText' class='tweetBloc'></div>");
	$(".tweetBloc").eq(0).text("Click Home or Search something to Start");
	
}

/* use a function for the exact format desired... */
function ISODateString(d){
  function pad(n){return n<10 ? '0'+n : n}
  var newDate =  d.getUTCFullYear()+'-'
      + pad(d.getUTCMonth()+1)+'-'
      + pad(d.getUTCDate())+'T'
      + pad(d.getUTCHours())+':'
      + pad(d.getUTCMinutes())+':'
      + pad(d.getUTCSeconds())+'Z';
   return newDate;
}




function isLoading(){
	$("#loadPanel").fadeIn('fast');
}
function doneLoading(callback){
	$("#loadPanel").fadeOut('slow', callback);
	$('#tweetSection').fadeTo(1);
}

function upcall ( text ){	
		try{			
			java.print(text);			
		}
		catch(e){
			$("body").prepend(e.message);
		}	
}

/*
	Remove all tweets
*/
function clearAll(){
	$('#tweetSection').fadeTo(1);
	$('#tweetSection').empty();
	
		
	/*isLoading();*/

}

/*
	Add a JSON tweet to the view (but by default it is hidden)
*/
function add( json ){
	
	json = json.replace("\\\"","\"");
	json = json.replace("\\'","'");
	/*json = json.replace("\\\n","\n");*/

	var tweetBloc = makeTweetBloc(json);

	$("#tweetSection").append(tweetBloc);
}

function makeTweetBloc(jsonTweet){

		try{
			var jsonTweet = JSON.parse(jsonTweet);	
		}
		catch(e){
			/* If a tweet can't be parsed we inhect the error in the view for the moment. By this way , we can see it*/
			return e;
		}
		
		
		var  id = jsonTweet.id;
		
		var  name = jsonTweet.name;
		var  screen_name= jsonTweet.screen_name;
		var  text = jsonTweet.text;
		var  time = new Date(jsonTweet.created_at);
		var isoDate = ISODateString(time);
		

		var formatedTime= time.getHours()+":"+time.getMinutes();

		var  pictureURL =  jsonTweet.pictureUrl;
		var twitterPrefix = jsonTweet.twitterPrefix;

		var profilUrl = twitterPrefix+screen_name;
		
		
		var  s = 	"<div class='tweetBloc' id='"+id+"'>"
										  +"<table>"	
										  +		"<tr>"
										  +		"<td></td>"
										  +			"<td colspan='2' >"
										  +				"<a class='name'>"+name+"</a>"
										  +				"&nbsp;&nbsp;"	/* soit deux espaces */
										  +				"<span class='screenName'>"
										  +					"<a href='"+profilUrl+"'>"+"@"+screen_name+"</a></span>"
										  +			"</td>"	
										  +			"<td class='time'>"
										  +				"<abbr class='timeago' title='"+isoDate+"'></abbr> "
										  					
										  +			"</td>"
										  +		"</tr>"
										  +		"<tr>"
										  +			"<td class='userPictureTD' rowspan='3'>"
										  
										  +					"<img class='userPicture' src='"+pictureURL+"'/>"
										  
										  +			"</td>"
										  +			"<td class='message' colspan='3'>"
										  +				text
										  +			"</td>"										 
										  +		"</tr>"
										  +		"<tr>"
										  +			"<td >"
										  +				"<table>"
										  +					"<tr class='options' >"
										  +						"<td class='favoriteBtn'>Add favorite</td>"
										  +						"<td class='retweetBtn'>Re-tweet</td>"
										  +					"</tr>"
										  +				"</table>"
										  +			"</td>"
										  +		"</tr>"
										  +		"<tr class='replyContainer'>"
										  +		"</tr>"
										  +	"</table>"
										  +"</div>";
		return s;
	}


/*Add the respond tools to a tweet*/

function appendRespondTools( domObject ){

	var tweet = $(domObject);
	$(tweet).addClass("replying");
	var html = 	"<td class='respondBar'>"
				+		"<div class='counter'>140</div>"
				+		"<textarea rows='4' cols='55'>"
				+	 		"Reply to "+$(tweet).find(".screenName").text()
				+		"</textarea>"

				
				+"</td>";
	
	$(tweet).find(".replyContainer").html(html);
	var textarea = $(tweet).find(".respondBar > textarea");
	$(textarea).on("click", function (event){
		event.stopPropagation();
		
		var text = $(this).html();
		text = text.replace("Reply to ", "");
		text += " ";
		$(this).html(text);
	}); 
	
	$(textarea).on('keydown', function (event){
		
		
		var text = $(this).val();
		$(this).siblings(".counter").text( 140 - text.length );


		if(text.length >= 140 ){
			$(this).val( text.substring(0,139) );
		}
		
	});
	
}



function showTweets(){
		
	formatTweets();
	
	doneLoading(function(){
		
			$(".tweetBloc").animate({
				opacity: 1
			}, 1000);
	});
	
}

function popup(link){
	java.createUserPopup(link);
}

/*Make the tweetBlocs usables, we add bindings, links etc...*/
function formatTweets(){

	

	$(".tweetBloc").each(function(){

		var screenName = $(this).find(".screenName");
		var link = $(screenName).text().replace("@", "");
		var a = "<a href='#' onclick=\"popup('"+link+"')\"></a>";

		$(screenName).children("a").attr("href","#");
		
		$(this).find(".name").wrap(a);


		/*Binding for tweet paddings and animations*/

		$(this).on("click", function(event){
			event.stopPropagation();
			$(this).siblings().animate({
				"margin": "0",
				"border-radius": "0px"
			},200);
			$(this).animate({
				"margin": "5px 0 5px 0",
				"border-radius": "10px"
			},200);

			if(!$(this).hasClass('replying')){
				appendRespondTools(this);
				$(this).find(".respondBar").on("click", function (event){
					event.stopPropagation();
				});
			}
			else{
				var replying = $(".replying");
				$(replying).find(".respondBar").remove();
				$(replying).removeClass("replying");
			}
			
		});


		/* bindings des options*/
		var optionRow = $(this).find(".options");
		$(this).on("mouseenter", function(event){
			$(optionRow).css("opacity","1");
		});

		$(this).on("mouseleave", function(event){
			$(optionRow).css("opacity","0.01");
		});


		// retweet binding

		
		$(optionRow).children(".retweetBtn").on("click", function(){
			var tweet = $(this).closest(".tweetBloc");
			$("body").prepend("|"+$(tweet).attr("retweet")+"|");
			if($(tweet).attr("retweet")=="true") deleteReTweet(tweet);
			else retweet(tweet);
		});



		// Add favorite binding

		$(optionRow).children(".favoriteBtn").on("click", function(){
			
			var id = $(this).closest(".tweetBloc").attr("id");
			
			var res = java.addFavorite(id);

			if(res){
				$(this).css("color","green").css("font-weight","bold");
			}

		});

	});

	$(".timeago").timeago();
}


function  retweet( domObject ){


	var tweet = domObject;
	var id = $(tweet).attr("id");
	
	var res = java.retweet(id);
	if(res){
		res = JSON.parse(res);
	}

	if(!res.error){
		$(tweet).attr("id", res.id);
		$(tweet).attr("retweet","true");
		
		$(domObject).addClass("btnActivated");
	}
	else{
		
		deleteReTweet(this);
	}
}

function deleteReTweet( domObject ){
	var tweet = domObject;
	var id = $(tweet).attr("id");
	var res = java.deleteReTweet(id);
	if(res){		
		$(this).removeClass("btnActivated");
		$(tweet).attr("retweet","false");
	}
}