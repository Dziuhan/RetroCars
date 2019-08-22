package ua.RetroCars.web.ResourceBundle;

import java.util.ListResourceBundle;
/**
 * Resource bundle for "ru" locale
 */


public class Page_ru extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		
		return contens;
	}
	
	static final Object[][] contens={
		{"header.cars","Машины"},
		{"header.Login","Логин"},
		{"header.Password","Пароль"},
		{"header.invalid_password","неверний пароль"},	
		{"header.loginRegistration","Логин / Регистрация"},
		{"header.ChangeLanguage","Выбрать :"},
		{"header.Manager","Менеджер"},
		{"header.Administrator","Администратор"},
		{"header.Logout","Выйти"},
		
		{"tableCars.Producer","Производитель"},
		{"tableCars.Rank","Ранг"},
		{"tableCars.SortBy","Сортировать по :"},
		{"tableCars.Foto","Фото"},
		{"tableCars.Make","Марка"},
		{"tableCars.Year","Год"},
		{"tableCars.Price","Цена"},
		{"tableCars.Choose","Выбрать"},
		{"tableCars.Filter","Фильтр"},
		{"tableCars.Sort","Сортировать"},
		{"tableCars.Reviews","Отзывы"},
		{"tableCars.byProducerReverse","убыванию производителя"},
		{"tableCars.byPriceReverse","убыванию цены"},
		{"tableCars.byRankReverse","убыванию ранга"},
		{"tableCars.byProducer","возрастанию производителя"},
		{"tableCars.byPrice","возрастанию цены"},
		{"tableCars.byRank","возрастанию ранга"},
		{"tableCars.Table","Таблица"},
		{"tableCars.List","Лист"},
		{"tableCars.Tile","Плитка"},
		{"tableCars."," "},
		{"tableCars.NotFoundCars"," Машины не найдены с такими параметрами"},
		
		{"orderClient.Rank","Ранг"},
		{"orderClient.Price1Day","Цена за 1 день"},
		{"orderClient.Driver","Водитель"},		
		{"orderClient.From","С"},
		{"orderClient.Before","по"},
		{"orderClient.Order","Заказать"},
		{"orderClient.Agree","Принять"},
		{"orderClient.Back","Назад"},
		{"orderClient.TotalPrice","Цена заказа"},
		
		{"reviewsCar.SendReview","Отослать отзыв"},
		{"reviewsCar.Choose","Выбрать машину"},
		
		{"cabinetClient.Car","Машина"},
		{"cabinetClient.Begin","Начало"},
		{"cabinetClient.End","Конец"},
		{"cabinetClient.State","Состояние"},
		{"cabinetClient.Price","Сумма"},
		{"cabinetClient.PayOrder","Оплатить аренду"},
		{"cabinetClient.PayCrush","Оплатить повреждение"},
		
		{"admin.NewCar","Новая машина"},
		{"admin.AllCars","Все машины"},
		{"admin.AllUsers","Все пользователи"},
		{"admin.SearchUser","Поиск пользователя"},
		{"admin.Producer","Производитель"},
		{"admin.Rank","Ранг"},
		{"admin.Foto","Фото"},
		{"admin.Image","Изображение"},
		{"admin.Year","Год"},
		{"admin.Make","Марка"},
		{"admin.Price","Цена"},
		{"admin.CreateNewCar","Создать новую машину"},		
		{"admin.SaveCar","Сохранить машину"},
		{"admin.DeleteCar","Удалить машину"},
		{"admin.Filter","Фильтр"},
		{"admin.Edit","Редактировать"},
		
		{"manager.NewOrders","Новые заказы"},
		{"manager.WaitingForPayment","Ждущие оплаты"},
		{"manager.PaidOrders","Оплаченные заказы"},
		{"manager.RejectedOrders","Отклоненые заказы"},
		{"manager.CrushCarOrders","Заказы с разбитой машиной"},
		{"manager.MayCloseOrders","Можно закрыть заказ"},
		{"manager.ClosedOrders","Закрытые заказы"},
		{"manager.AllOrders","Все заказы"},
		{"manager.Login","Логин"},
		{"manager.Car","Машина"},
		{"manager.Begin","Начало"},
		{"manager.End","Конец"},
		{"manager.State","Состояние"},
		{"manager.Driver","Водитель"},
		{"manager.PriceOrder","Сумма заказа"},
		{"manager.PriceCrush","Сумма поломки"},	
		
	};

}
