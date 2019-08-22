package ua.RetroCars.web.ResourceBundle;

import java.util.ListResourceBundle;

/**
 * Resource bundle for "en" locale
 *
 */
public class Page_en extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		
		return contens;
	}
	
	static final Object[][] contens={
		{"header.cars","Cars"},
		{"header.Login","Login"},
		{"header.Password","Password"},
		{"header.invalid_password","invalid password"},	
		{"header.loginRegistration","Login / Registration"},
		{"header.ChangeLanguage","Change :"},
		{"header.Manager","Manager"},
		{"header.Administrator","Admin"},
		{"header.Logout","Logout"},
		
		{"tableCars.Producer","Producer"},
		{"tableCars.Rank","Rank"},
		{"tableCars.SortBy","Sort by :"},
		{"tableCars.Foto","Foto"},
		{"tableCars.Make","Make"},
		{"tableCars.Year","Year"},
		{"tableCars.Price","Price"},
		{"tableCars.Choose","Choose"},
		{"tableCars.Filter","Filter"},
		{"tableCars.Sort","Sort"},
		{"tableCars.Reviews","Reviews"},
		{"tableCars.byProducerReverse","Producer reverse"},
		{"tableCars.byPriceReverse","Price reverse"},
		{"tableCars.byRankReverse","Rank reverse"},
		{"tableCars.byProducer","Producer"},
		{"tableCars.byPrice","Price"},
		{"tableCars.byRank","Rank"},
		{"tableCars.Table","Table"},
		{"tableCars.List","List"},
		{"tableCars.Tile","Tile"},
		
		{"tableCars."," "},
		{"tableCars.NotFoundCars","Not found cars with given filters "},
		
		{"orderClient.Rank","Rank"},
		{"orderClient.Price1Day","Price for 1 day"},
		{"orderClient.Driver","Driver"},		
		{"orderClient.From","From"},
		{"orderClient.Before","before"},
		{"orderClient.Order","Order"},
		{"orderClient.Agree","Agree"},
		{"orderClient.Back","Back"},
		{"orderClient.TotalPrice","Price for order"},
		
		
		{"reviewsCar.SendReview","Send review"},
		{"reviewsCar.Choose","Choose car"},
		
		{"cabinetClient.Car","Car"},
		{"cabinetClient.Begin","Begin"},
		{"cabinetClient.End","End"},
		{"cabinetClient.State","State"},
		{"cabinetClient.Price","Price"},
		{"cabinetClient.PayOrder","Pay rent"},
		{"cabinetClient.PayCrush","Pay crush"},
		
		{"admin.NewCar","New car"},
		{"admin.AllCars","All cars"},
		{"admin.AllUsers","All users"},
		{"admin.SearchUser","Search user"},
		{"admin.Producer","Producer"},
		{"admin.Rank","Rank"},
		{"admin.Foto","Foto"},
		{"admin.Image","Image"},
		{"admin.Year","Year"},
		{"admin.Make","Make"},		
		{"admin.Price","Price"},
		{"admin.CreateNewCar","Create new car"},		
		{"admin.SaveCar","Save car"},
		{"admin.DeleteCar","Delete car"},
		{"admin.Filter","Filter"},
		{"admin.Edit","Edit"},
		
		{"manager.NewOrders","New orders"},
		{"manager.WaitingForPayment","Waiting for payment"},
		{"manager.PaidOrders","Paid orders"},
		{"manager.RejectedOrders","Rejected orders"},
		{"manager.CrushCarOrders","Orders with crushed car"},
		{"manager.MayCloseOrders","May close orders"},
		{"manager.ClosedOrders","Closed orders"},
		{"manager.AllOrders","All orders"},
		{"manager.Login","Login"},
		{"manager.Car","Car"},
		{"manager.Begin","Begin"},
		{"manager.End","End"},
		{"manager.State","State"},
		{"manager.Driver","Driver"},
		{"manager.PriceOrder","Price order"},
		{"manager.PriceCrush","Price crush"},	
		
	};

}
