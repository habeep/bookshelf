package com.tansha.library.bookshelf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.tansha.library.bookshelf.model.SearchResult;
import com.tansha.library.bookshelf.repository.BookRepositoryForJdbc;

@Repository
public class JdbcBookRepository implements BookRepositoryForJdbc {

	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<SearchResult> selectSearchBooks(int publisherId, int categoryId,int languageId,int readingLevelId) {
		
		//publisherId,categoryId,languageId,readingLevelId
		StringBuilder sbSearchStr = new StringBuilder();
		if(readingLevelId != -1 ) {
			sbSearchStr.append(" AND book.readingLevelId = " + readingLevelId );	
		}

		if(categoryId != -1 ) {
			sbSearchStr.append(" AND book.categoryID = " + categoryId );	
		}

		if(languageId != -1 ) {
			sbSearchStr.append(" AND book.languageId = " + languageId );	
		}

		if(publisherId != -1 ) {
			sbSearchStr.append(" AND book.publisherID = " + publisherId );	
		}
		String searchStr = sbSearchStr.toString();
		if(searchStr.length() > 4) {
			searchStr = searchStr.substring(4, searchStr.length());
		} else {
			searchStr = " book.publisherID = -1 OR book.categoryID = -1 OR book.languageId = -1 OR  book.readingLevelId = -1 ";
		}

		String SEARCHQUERY = "select book.bookId,book.bookTitle, publisher.publisherName,authors.authorName,bookCategory.categoryName,book.isbncode,readingLevels.readingLevel,readingLevels.Id,languages.language,book.noofPages,book.binding  from tbl_books_details book ";
		
		SEARCHQUERY +=			" inner join  tbl_publisher_master publisher " ;  
		SEARCHQUERY +=			"	on book.publisherID=publisher.publisherId ";
		//SEARCHQUERY +=			" inner join tbl_borrow_master  bookborrow "; 
		//SEARCHQUERY +=			" ON book.bookId = bookborrow.bookID "; 
		SEARCHQUERY +=			" inner join tbl_authors_master  authors "; 
		SEARCHQUERY +=			"	ON book.authorID = authors.authorID "; 
		SEARCHQUERY +=			" inner join tbl_reading_level  readingLevels "; 
		SEARCHQUERY +=			"	ON book.readingLevelId = readingLevels.Id "; 
		SEARCHQUERY +=			" inner join tbl_language_master languages "; 
		SEARCHQUERY +=			"	on book.languageId = languages.languageID "; 
		SEARCHQUERY +=			" inner join tbl_books_category_master  bookCategory "; 
		SEARCHQUERY +=			"	ON bookCategory.categoryId = book.categoryID "; 
		SEARCHQUERY +=			" where ( "+searchStr+")"; 
		//SEARCHQUERY +=			" OR authors.authorName like %?1% OR languages.language like %?1% OR bookCategory.categoryName like %?1% ) "; 
		SEARCHQUERY +=			" AND book.isBookBorrowed = 0 AND book.isActive = 1 AND book.isLost = 0 group by book.isbncode ";
		System.out.println("SearchQuery >>> "+SEARCHQUERY);
		List<SearchResult> sr = new ArrayList<SearchResult>();
		jdbcTemplate.query(SEARCHQUERY, new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				while (resultSet.next()) {
					SearchResult srObj = new SearchResult();
					srObj.setBookId(resultSet.getInt(1));
					srObj.setBookTitle(resultSet.getString(2));
					srObj.setPublisherName(resultSet.getString(3));
					srObj.setAuthorName(resultSet.getString(4));
					srObj.setCategoryName(resultSet.getString(5));
					srObj.setIsbncode(resultSet.getString(6));
					srObj.setReadingLevel(resultSet.getString(7));
					srObj.setReadingLevelId(resultSet.getInt(8));
					srObj.setLanguage(resultSet.getString(9));
					srObj.setNoofPages(resultSet.getInt(10));
					srObj.setBinding(resultSet.getString(11));
					//System.out.println(srObj.toString()+"\n\n");
					sr.add(srObj);
					//String name = resultSet.getString("Name");
					// process it
				}
			}
		});
		
		//System.out.println("SR >>>>> "+sr.toString());
		return sr;
		
/*		return jdbcTemplate.query(
                SEARCHQUERY,
                (rs, rowNum) ->
                        new SearchResult(
                        		rs.getInt(0),
                        		rs.getString(1)
                        )
        );
*/		/*List<SearchResult> obj = new ArrayList<Object[]>();
		
		//List<Map<String, Object>> obj = jdbcTemplate.queryForList(SEARCHQUERY, new Object[]);
		jdbcTemplate.query(SEARCHQUERY, new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				while (resultSet.next()) {
					Object obj = new Object();
					resultSet.getRowId(1);
					String name = resultSet.getString("Name");
					// process it
				}
			}
		});
		*/
		//List<SearchResult> obj1 = (List<SearchResult>) jdbcTemplate.queryForObject(SEARCHQUERY, SearchResult.class);
		//System.out.println("error 1");
		
		// TODO Auto-generated method stub
		//return obj1;
	}

}
