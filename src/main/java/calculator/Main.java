package calculator;

import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class Main implements CommandLineRunner {
	
	private static final int RUNE_BAR = 2363;
	private static final int COAL = 453;
	private static final int NATURE_RUNE = 561;
	private static final int RUNITE_ORE = 451;
	private static final int ADAMANT_CROSSBOW = 9183;
	private static final int STEEL_BAR = 2353;
	private static final int CANNONBALL = 2;
	private static final int GOLD_ORE = 444;
	private static final int GOLD_BAR = 2357;
	
	
	
    private static final Logger log = LoggerFactory.getLogger(Main.class); 
	public static final String itemApiUri = "http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json";
    
	public static void main(String[] args) {
        SpringApplication.run(Main.class);		
	}
	
	@Override
	public void run(String... args) throws Exception {
		getSuperHeatRuneBarProfit(); 
		getCannonballSmithingProfit();
		getGoldSmithingProfit();
    }
	
	private double getPrice(int itemNumber) {
		ItemResult itemResult = null;
		try {
			itemResult = getItem(itemNumber);
		} catch (IOException e) {}
		Item item = itemResult.getItem();
		return parseRunescapePrice(item.getCurrent().getPrice());
	}
	private ItemResult getItem(int itemNumber) throws JsonParseException, JsonMappingException, IOException {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(itemApiUri).queryParam("item", itemNumber);
        RestTemplate restTemplate = new RestTemplate();
        
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
         
        ResponseEntity<String> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ItemResult itemResult = objectMapper.readValue(result.getBody(), ItemResult.class);
        return itemResult;		
	}
  
	private double parseRunescapePrice(String price) {
		price = price.replace(",", "");
		if (price.contains("k")) {
			price = price.replace("k", "");
			double priceDouble = Double.parseDouble(price);
			priceDouble = priceDouble * 1000;
			return priceDouble;
		}
		return Double.parseDouble(price);
	}
	
	private double getSuperHeatRuneBarProfit() {
		final double RUNE_BAR_PRICE = getPrice(RUNE_BAR);
		final double RUNITE_ORE_PRICE = getPrice(RUNITE_ORE);
		final double NATURE_RUNE_PRICE = getPrice(NATURE_RUNE);
		final double COAl_PRICE = getPrice(COAL);
		
		double profit = RUNE_BAR_PRICE - RUNITE_ORE_PRICE - (8 * COAl_PRICE) - NATURE_RUNE_PRICE; 
		log.info("profit per rune bar: " + profit);
		return profit;
	}
	
	private double getCannonballSmithingProfit() {
		final double STEEL_BAR_PRICE = getPrice(STEEL_BAR);
		final double CANNONBALL_PRICE = getPrice(CANNONBALL);
		
		double profit = (CANNONBALL_PRICE * 4) - STEEL_BAR_PRICE;
		log.info("profit per steel bar: " + profit);
		return profit;
	}
	
	private double getGoldSmithingProfit() {
		final double GOLD_ORE_PRICE = getPrice(GOLD_ORE);
		final double GOLD_BAR_PRICE = getPrice(GOLD_BAR);
		
		double profit = GOLD_BAR_PRICE - GOLD_ORE_PRICE;
		log.info("profit per gold bar: " + profit);
		return profit;
	}

}
