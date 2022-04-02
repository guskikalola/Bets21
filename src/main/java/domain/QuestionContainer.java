package domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class QuestionContainer {
	Event event;
	Question question;

	public QuestionContainer(Question q) {
		this.question = q;
		this.event = q.getEvent();
	}

	public QuestionContainer() {
		question = null;
		event = null;
	}

	public Event getEvent() {
		return event;
	}

	public Question getQuestion() {
		return question;
	}

	public String toString() {
		return question + "/" + event;
	}
}
