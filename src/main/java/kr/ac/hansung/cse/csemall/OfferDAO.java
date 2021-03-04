package kr.ac.hansung.cse.csemall;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

@Component("offerDao") //bean으로 등록됨. 그리고 dataSource가 자동 주입됨.
public class OfferDao {
	//정해진 코드
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	//jdbcTemplate 을 만들어서 dao가 쓴다.
	
	///////////////////////////////////////////
	public int getRowCount() {
		String sqlStatement = "select count(*) from offers";
		return jdbcTemplate.queryForObject(sqlStatement, Integer.class);
		//sql문, return타입
	}
	
	//cRud - singleObject
	//query and return a single object
	public Offer getOffer(String name) {
		String sqlStatement = "select * from offers where name = ?";
		return jdbcTemplate.queryForObject(sqlStatement,new Object[] {name}, new RowMapper<Offer>() {
			@Override
			public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Offer offer = new Offer();
				offer.setId(rs.getInt("id"));
				offer.setName(rs.getString("name"));
				offer.setEmail(rs.getString("email"));
				offer.setText(rs.getString("text"));
				
				return offer;
			}
		});
	}
	//cRud - multiple objects
	//query and return multiple objects
	public List <Offer> getOffers() {
		String sqlStatement = "select * from offers";
		return jdbcTemplate.query(sqlStatement, new RowMapper<Offer>() {
			@Override
			public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Offer offer = new Offer();
				offer.setId(rs.getInt("id"));
				offer.setName(rs.getString("name"));
				offer.setEmail(rs.getString("email"));
				offer.setText(rs.getString("text"));
				return offer;
			}
		});
	}
	
	//Crud - Create object
	public boolean insert(Offer offer) {
		String name = offer.getName();
		String email = offer.getEmail();
		String text = offer.getText();
		
		String sqlStatement = "insert into offers (name, email, text) values (?,?,?)";
		
		return (jdbcTemplate.update(sqlStatement, new Object[] {name,email,text}) == 1 );
	}
	
	//crUd - Update object
	public boolean update(Offer offer) {
		int id = offer.getId();
		String name = offer.getName();
		String email = offer.getEmail();
		String text = offer.getText();
		
		String sqlStatement = "update offers set name = ?, email=?, text=? where id =?";
		
		return (jdbcTemplate.update(sqlStatement, new Object[] {name,email,text,id}) == 1 );
	}
	
	//cruD - delete object
	public boolean delete(int id) {
		String sqlStatement = "delete from offers where id =?";
		return (jdbcTemplate.update(sqlStatement, new Object[] {id}) == 1 );
	}
}
