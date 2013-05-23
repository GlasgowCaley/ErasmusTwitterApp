package src.org.tweet_tea.model;

/**
 * Define an Error , parsed from JSON
 * @author Geoffrey
 *
 */
public class ErrorWrapper {

		private Error[] errors;
		
		public Error[] getErrors(){
			return errors;
		}
}
