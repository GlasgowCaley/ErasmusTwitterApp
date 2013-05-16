jQuery.fn.setCounter = function(maxLength){
 
  return this.each(function(){
 
    var jqthis = jQuery(this);
 
    if (jqthis.is('textarea')){
 
      var counterId = jqthis.attr('id') + "_counter";
 
      var htmlCounter = '<div id="'+counterId+'" class="tac"><span class="tac_length">'+jqthis.val().length+'</span>';
 
      if (maxLength){
        htmlCounter += '<span class="tac_sep">/</span><span class="tac_maxLength">'+maxLength+'</span>';
      }
 
      htmlCounter += '</div>';
 
      jqthis.after(htmlCounter);
 
      jqthis.bind("keyup",function(){
 
        var content = jqthis.val();
 
        if (maxLength && content.length > maxLength){
          jqthis.val( content.substring(0,maxLength) );
        }
 
        jQuery('#' + counterId + " > span.tac_length").text(content.length);        
 
      });
 
    } else {
      console.log("Not applicable to element: " + jqthis);
    }
  });
}

