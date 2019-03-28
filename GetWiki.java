package crystalchain;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;

public class GetWiki {

	public static void main(String[] args) throws IOException {
		JOptionPane jop = new JOptionPane();

		String urlWithoutWord = "https://en.wikipedia.org/w/api.php?action=parse&section=0&prop=text&format=json&page=";
		String word = "topic";
		word = JOptionPane.showInputDialog(jop,
				"Entrez le mot à rechercher sur\n" + urlWithoutWord, word);
		// annul -> null; x -> null

		if (word != null) {
			System.out.println(word);
			System.out.println(urlWithoutWord + word);

			URL url = new URL(urlWithoutWord + word);
			URLConnection uRLConnection = url.openConnection();
			System.out.println(uRLConnection.getContentType());

			Reader reader = new InputStreamReader(
					uRLConnection.getInputStream(), Charset.forName("UTF-8"));
			char[] buffer = new char[1024];
			int count;
			StringBuilder strBuilder = new StringBuilder();
			while ((count = reader.read(buffer)) != -1)
				strBuilder.append(buffer, 0, count);
			String jsonString = strBuilder.toString();
			System.out.println(jsonString);
			//java n'a pas de parseur json dans les bibliotheque standard, on fait en manuel
			
			//on enleve le debut
			String[] temp = jsonString.split("\"[*]\":\"");
			jsonString = temp[1];
			System.out.println(jsonString);
			//on enleve le fin
			jsonString = jsonString.replaceAll("\"}}}$", " ");
			System.out.println(jsonString);
			//on enleve les balises
			jsonString = jsonString.replaceAll("<[^>]*>", " ");
			System.out.println(jsonString);
			String jsonString2 = jsonString.toLowerCase();
			System.out.println(jsonString2);
			
			int occurences = 0;
			while (jsonString.matches(".*\\W" + word + "\\W.*")) {
				occurences++;
				jsonString = jsonString.replaceFirst(word, " ");
				System.out.println(occurences + " " + jsonString);
			}
			System.out.println(occurences);
			
			int occurences2 = 0;
			String word2 = word.toLowerCase();
			while (jsonString2.matches(".*\\W" + word2 + "\\W.*")) {
				occurences2++;
				jsonString2 = jsonString2.replaceFirst(word2, " ");
				System.out.println(occurences2 + " " + jsonString2);
			}
			System.out.println(occurences2);
			
			
			jop.showMessageDialog(jop, "Mot \"" + word + "\" apparait " + occurences + " fois (case sensitive)\n"
					+ "Mot \"" + word2 + "\" apparait " + occurences2 + " fois (case INsensitive)\n", 
					"Occurences", jop.PLAIN_MESSAGE);
			
		}
	}
}
