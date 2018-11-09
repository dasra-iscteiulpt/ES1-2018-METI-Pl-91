import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

import com.restfb.types.Post;

import twitter4j.Status;

public class GenericMessage {

	private String dateM;
	private String canalM;
	private String fromM;
	private String titleM;
	private String contentM;
	
	public GenericMessage(String dateM, String canalM, String fromM, String titleM, String contentM) {
		this.dateM = dateM;
		this.canalM = canalM;
		this.fromM = fromM;
		this.titleM = titleM;
		this.contentM = contentM;
	}
	
	public static ArrayList<GenericMessage> receiveMailReturnMessage(ArrayList<Message> listMail) {
		ArrayList<GenericMessage> genMessage = new ArrayList<GenericMessage>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateM;
		String canalM;
		String fromM;
		String titleM;
		String contentM;
		for(Message mail: listMail) {
			try {
				dateM = sdf.format(mail.getReceivedDate());
				canalM = "E-Mail";
				fromM = mail.getFrom()[0].toString();
				titleM = mail.getSubject().toString();
				contentM = ReadEmails.getBody(mail);
				genMessage.add(new GenericMessage(dateM, canalM, fromM, titleM, contentM));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return genMessage;
	}
	
	public static ArrayList<GenericMessage> receiveTweetsReturnMessage(List<Status> listTweets) {
		ArrayList<GenericMessage> genMessage = new ArrayList<GenericMessage>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateM;
		String canalM;
		String fromM;
		String titleM;
		String contentM;
		for(Status tweet: listTweets) {
			try {
				dateM = sdf.format(tweet.getCreatedAt());
				canalM = "Twitter";
				fromM = tweet.getUser().getName();
				titleM = Long.toString(tweet.getId());
				contentM = tweet.getText(); 
				genMessage.add(new GenericMessage(dateM, canalM, fromM, titleM, contentM));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return genMessage;
	}
	
	public static ArrayList<GenericMessage> receivePostsReturnMessage(List<Post> listPosts) {
		ArrayList<GenericMessage> genMessage = new ArrayList<GenericMessage>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateM;
		String canalM;
		String fromM;
		String titleM;
		String contentM;
		for(Post post: listPosts) {
			try {
				dateM = sdf.format(post.getCreatedTime());
				canalM = "Facebook";
				fromM = "Teste";
				titleM = post.getId();
				contentM = post.getMessage(); 
				genMessage.add(new GenericMessage(dateM, canalM, fromM, titleM, contentM));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return genMessage;
	}

	public String getContentM() {
		return contentM;
	}

	public String getCanalM() {
		return canalM;
	}

	public String getDateM() {
		return dateM;
	}

	public String getFromM() {
		return fromM;
	}

	public String getTitleM() {
		return titleM;
	}
	
}