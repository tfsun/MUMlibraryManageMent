package Services;

import model.Book;
import model.Periodical;
import model.Publication;
//import sun.security.action.GetBooleanAction;

public class CopyService {
	static public String getCopyIDbyPublication(Publication publication)
	{
		String copyNo="";
		if (publication.getClass().getSimpleName().equals("Book")) {
			copyNo = "Book_" + ((Book)publication).getIsbn() + "_" + ((Book)publication).getCurID();
		}
		else if (publication.getClass().getSimpleName().equals("Periodical")) {
			copyNo = "Periodicaal_" + ((Periodical)publication).getTitle() + "_" + ((Periodical)publication).getIssueNumber() + "_" + ((Periodical)publication).getCurID();
		}
		return copyNo;
	}
}
