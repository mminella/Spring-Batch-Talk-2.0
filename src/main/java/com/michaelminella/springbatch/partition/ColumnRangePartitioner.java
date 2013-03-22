package com.michaelminella.springbatch.partition;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

public class ColumnRangePartitioner implements Partitioner {

	protected static final Log logger = LogFactory.getLog(ColumnRangePartitioner.class);

	private static final int PARTITOINS_PER_NODE = 5;

	private JdbcOperations jdbcTemplate;

	private String table;

	private String column;

	/**
	 * The name of the SQL table the data are in.
	 *
	 * @param table the name of the table
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * The name of the column to partition.
	 *
	 * @param column the column name.
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * The data source for connecting to the database.
	 *
	 * @param dataSource a {@link DataSource}
	 */
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Partition a database table assuming that the data in the column specified
	 * are uniformly distributed. The execution context values will have keys
	 * <code>minValue</code> and <code>maxValue</code> specifying the range of
	 * values to consider in each partition.
	 *
	 * @see Partitioner#partition(int)
	 */
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		int partitionCount = gridSize * PARTITOINS_PER_NODE;
		int min = jdbcTemplate.queryForInt("SELECT MIN(" + column + ") from " + table);
		int max = jdbcTemplate.queryForInt("SELECT MAX(" + column + ") from " + table);
		int count = jdbcTemplate.queryForInt("SELECT count(*) from " + table);
		int targetSize = (max - min) / partitionCount + 1;
		logger.debug("***********************************************************");
		logger.debug("count = " + count + " min = " + min + " max = " + max + " targetSize = " + targetSize);
		logger.debug("***********************************************************");

		Map<String, ExecutionContext> result = new HashMap<String, ExecutionContext>();
		int number = 0;
		int start = min;
		int end = start + targetSize - 1;

		while (start <= max) {

			ExecutionContext value = new ExecutionContext();
			result.put("partition" + number, value);

			if (end >= max) {
				end = max;
			}
			value.putLong("minValue", start);
			value.putLong("maxValue", end);
			start += targetSize;
			end += targetSize;
			number++;
		}

		logger.debug("***********************************************************");
		logger.debug("RESULT.SIZE = " + result.size());
		logger.debug("***********************************************************");

		return result;

	}

}
