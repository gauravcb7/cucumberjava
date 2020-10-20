#Author: gaurav.x.srivastava@gmail.com

@demo
Feature: Asset purchase e2e

   Scenario: Adding asset to cart and checkout
   Given Customer open URL "https://www.demoblaze.com/"
   When Customer clicks on "Laptops" by locator "xpath" and value "//a[contains(text(),'Laptops')]"
	 Then Customer clicks on "Sony vaio i5" by locator "xpath" and value "//a[contains(text(),'Sony vaio i5')]"
	 Then Customer clicks on "Add to cart" by locator "xpath" and value "//a[contains(text(),'Add to cart')]"
   Then Customer accept pop up confirmation
   Then Customer clicks on "Home page" by locator "xpath" and value "//a[contains(text(),'Home ')]"
   When Customer clicks on "Laptops" by locator "xpath" and value "//a[contains(text(),'Laptops')]"
	 Then Customer clicks on "Dell i7 8gb" by locator "xpath" and value "//a[contains(text(),'Dell i7 8gb')]"
	 Then Customer clicks on "Add to cart" by locator "xpath" and value "//a[contains(text(),'Add to cart')]"
   Then Customer accept pop up confirmation
   Then Customer clicks on "Cart" by locator "xpath" and value "//a[contains(text(),'Cart')]"
   Then Customer deletes item "Dell i7 8gb" from cart
   Then Customer clicks on "Place order button" by locator "xpath" and value "//button[contains(text(),'Place Order')]"
   Then Customer fills web form with details name as "Gaurav Srivastava" country as "India" city as "Gurgaon" credit card as "1234654389761234" month as "02" year as "20"
   Then Customer clicks on "Place order button" by locator "xpath" and value "//button[contains(text(),'Purchase')]"
   And Customer logs purchase infromation
   Then Customer verify the "Purchase amount" should be equals to "790"
   Then Customer clicks on "Ok button" by locator "xpath" and value "//button[contains(text(),'OK')]"