package ch.brunostuessy.algo.breakout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.DoubleStream;

/**
 * Provides a double stream of close prices from a CSV file with the format:
 * 
 * <pre>
 * dateTime,open,low,high,close
 * 03.01.2001,0.9507,0.9262,0.9569,0.9271
 * 04.01.2001,0.9271,0.9269,0.9515,0.9507
 * </pre>
 *
 * @author Bruno Stüssi
 *
 */
public final class CSVClosePriceDoubleStreamProvider {

	/**
	 * Provides a double stream of close prices from a CSV file.
	 * 
	 * @param candleFilePath
	 * @return
	 * @throws IOException
	 */
	public DoubleStream closePrices(final String candleFilePath) throws IOException {
		// final DateFormat df = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

		return Files.lines(Paths.get(candleFilePath)).skip(1).mapToDouble(candleData -> {
			final String[] candleColumns = candleData.split(",", -1);

			// final Date date = df.parse(candleColumns[0]);
			// final double open = Double.parseDouble(candleColumns[1]);
			// final double low = Double.parseDouble(candleColumns[2]);
			// final double high = Double.parseDouble(candleColumns[3]);
			final double close = Double.parseDouble(candleColumns[4]);

			return close;
		});
	}

}
