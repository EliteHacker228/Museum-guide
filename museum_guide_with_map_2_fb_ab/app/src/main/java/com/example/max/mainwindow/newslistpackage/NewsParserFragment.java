package com.example.max.mainwindow.newslistpackage;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.max.mainwindow.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewsParserFragment extends Fragment {

    MyRecyclerViewAdapter adapter;
    SharedPreferences sharedPreferences;


   private Elements content;
RecyclerView recyclerView;
   private ArrayList<NewsElement> newsList = new ArrayList<>();
   private ArrayList<NewsElement> savedNewsList = new ArrayList<>();
   private ArrayList<NewsElement> sourceList = new ArrayList<>();
   boolean sl=false;


    public NewsParserFragment() {
        // Required empty public constructor
    }

    private void fullSourceList(){
        //sourceList.add( new NewsElement("Архипов объяснил, как Исторический сквер Екатеринбурга превратился в стоянку ", " 09.04.2018 ", "13:12", "https://img.znak.com/1934824.jpg" ,"https://znak.com/2018-04-09/arhipov_obyasnil_kak_istoricheskiy_skver_ekaterinburga_prevratilsya_v_stoyanku", "2018.04.09"));

//        sourceList.add( new NewsElement("Почти два миллиона — на охрану, еще столько же — на «Мерседес» Музей «Россия — Моя история» в Екатеринбурге переводят на баланс бюджета. Сколько это стоит ", " 10.05.2018 ", "18:30", "https://img.znak.com/1991821.jpg" ,"https://znak.com/2018-05-10/muzey_rossiya_moya_istoriya_v_ekaterinburge_perevodyat_na_balans_byudzheta_skolko_eto_stoit", "2018.05.10"));
//        sourceList.add( new NewsElement("Открытие комнаты-музея Левитана в Екатеринбурге отложили до 2020 года ", " 08.05.2018 ", "12:11", "https://img.znak.com/1987413.jpg" ,"https://znak.com/2018-05-08/otkrytie_komnaty_muzeya_levitana_v_ekaterinburge_otlozhili_do_2020_goda", "2018.05.08"));
//        sourceList.add( new NewsElement("Губернатор Куйвашев встретится с топ-менеджером Coca-Cola. Будут говорить о кубке ЧМ-2018 ", " 08.05.2018 ", "11:04", "https://img.znak.com/1987321.jpg" ,"https://znak.com/2018-05-08/gubernator_kuyvashev_vstretitsya_s_top_menedzherom_coca_cola_budut_govorit_o_kubke_chm_2018", "2018.05.08"));
//        sourceList.add( new NewsElement("Фондохранилище «Эрмитаж-Урал» обойдется чуть дешевле музея «Россия — Моя история» ", " 07.05.2018 ", "13:08", "https://img.znak.com/1985464.jpg" ,"https://znak.com/2018-05-07/fondohraniliche_ermitazh_ural_oboydetsya_chut_deshevle_muzeya_rossiya_moya_istoriya", "2018.05.07"));
//        sourceList.add( new NewsElement("Содержание парка «Россия — Моя история» в Екатеринбурге переложили на бюджет региона ", " 03.05.2018 ", "10:52", "https://img.znak.com/1978388.jpg" ,"https://znak.com/2018-05-03/soderzhanie_parka_rossiya_moya_istoriya_v_ekaterinburge_perelozhili_na_byudzhet_regiona", "2018.05.03"));
//        sourceList.add( new NewsElement("Эскиз одного из павильонов будущего зоопарка «Пороховая бочка» Участники слушаний одобрили проект района, где построят новый зоопарк Екатеринбурга ", " 27.04.2018 ", "14:30", "https://img.znak.com/1969095.jpg" ,"https://znak.com/2018-04-27/uchastniki_slushaniy_odobrili_proekt_rayona_gde_postroyat_novyy_zoopark_ekaterinburga", "2018.04.27"));
//        sourceList.add( new NewsElement("Телекомпания, которой есть что вспомнить Четвертому каналу 27 лет. 27 фотографий с вечеринки ", " 27.04.2018 ", "10:30", "https://tps://img.znak.com/1968465.jpg" ,"https://znak.com/2018-04-27/4_mu_kanalu_27_let_27_fotografiy_s_vecherinki", "2018.04.27"));
//        sourceList.add( new NewsElement("Биография безгрешного руководителя Вышла в свет книга о первом мэре Екатеринбурга Аркадии Чернецком ", " 20.04.2018 ", "18:09", "https://img.znak.com/1956460.jpg" ,"https://znak.com/2018-04-20/vyshla_v_svet_kniga_o_pervom_mere_ekaterinburga_arkadii_cherneckom", "2018.04.20"));
//        sourceList.add( new NewsElement("Чернецкий назвал «стержневого человека» в команде мэрии Екатеринбурга. И это не Кожемяко ", " 20.04.2018 ", "17:04", "https://img.znak.com/1956348.jpg" ,"https://znak.com/2018-04-20/cherneckiy_nazval_sterzhnevogo_cheloveka_v_komande_merii_ekaterinburga_i_eto_ne_kozhemyako", "2018.04.20"));
//        sourceList.add( new NewsElement("Николай Панафидин (с цветами) на открытии выставки Челябинск «С точки зрения физики, этого не может быть. Но оно есть, и оно работает» Короткая выставка мобилей Николая Панафидина вновь потрясла челябинцев ", " 18.04.2018 ", "11:59", "https://img.znak.com/1951812.jpg" ,"https://znak.com/2018-04-18/korotkaya_vystavka_mobiley_nikolaya_panafidina_vnov_potryasla_chelyabincev", "2018.04.18"));
//        sourceList.add( new NewsElement("Смотровая площадка и кофе навынос: как реконструируют водонапорную башню на Плотинке ", " 17.04.2018 ", "14:16", "https://img.znak.com/1950026.jpg" ,"https://znak.com/2018-04-17/smotrovaya_plochadka_i_kofe_navynos_kak_rekonstruiruyut_vodonapornuyu_bashnyu_na_plotinke", "2018.04.17"));
//        sourceList.add( new NewsElement("В Екатеринбурге выходит книга о первом мэре города «Чернецкий: совершеннолетие власти» ", " 13.04.2018 ", "19:01", "https://img.znak.com/1943704.jpg" ,"https://znak.com/2018-04-13/v_ekaterinburge_vyhodit_kniga_o_pervom_mere_goroda_cherneckiy_sovershennoletie_vlasti", "2018.04.13"));
//        sourceList.add( new NewsElement("«Космос наш!» В Екатеринбурге открылась выставка о космонавтике и авиации ", " 12.04.2018 ", "16:00", "https://img.znak.com/1941355.jpg" ,"https://znak.com/2018-04-12/v_ekaterinburge_otkrylas_vystavka_o_kosmonavtike_i_aviacii", "2018.04.12"));
//        sourceList.add( new NewsElement("Содержание Центрального стадиона в Екатеринбурге после ЧМ-2018 обойдется в 300 млн в год ", " 11.04.2018 ", "10:15", "https://img.znak.com/1938579.jpg" ,"https://znak.com/2018-04-11/soderzhanie_centralnogo_stadiona_v_ekaterinburge_posle_chm_2018_oboydetsya_v_300_mln_v_god", "2018.04.11"));
//        sourceList.add( new NewsElement("Архипов объяснил, как Исторический сквер Екатеринбурга превратился в стоянку ", " 09.04.2018 ", "13:12", "https://img.znak.com/1934824.jpg" ,"https://znak.com/2018-04-09/arhipov_obyasnil_kak_istoricheskiy_skver_ekaterinburga_prevratilsya_v_stoyanku", "2018.04.09"));
//        sourceList.add( new NewsElement("От концерта лидера «Чайф» до Микеланджело Самые интересные мероприятия Ельцин Центра в апреле ", " 30.03.2018 ", "16:49", "https://img.znak.com/1917444.jpg" ,"https://znak.com/2018-03-30/ot_besplatnogo_koncerta_chayf_do_mikelandzhelo", "2018.03.30"));
//        sourceList.add( new NewsElement("УГМК назвала «копеечным» ущерб, нанесенный музею «Россия — моя история» взрывом телебашни ", " 26.03.2018 ", "14:57", "https://img.znak.com/1908591.jpg" ,"https://znak.com/2018-03-26/ugmk_nazvala_kopeechnym_ucherb_nanesennyy_muzeyu_rossiya_moya_istoriya_vzryvom_telebashni", "2018.03.26"));
//        sourceList.add( new NewsElement("Фасад музея «Россия — моя история» в Екатеринбурге пострадал при сносе телебашни ", " 25.03.2018 ", "16:03", "https://img.znak.com/1906971.jpg" ,"https://znak.com/2018-03-25/fasad_muzeya_rossiya_moya_istoriya_v_ekaterinburge_postradal_pri_snose_telebashni", "2018.03.25"));
//        sourceList.add( new NewsElement("В Екатеринбурге к ЧМ-2018 появятся информационные пункты за 3,1 млн рублей ", " 23.03.2018 ", "15:22", "https://img.znak.com/1903630.jpg" ,"https://znak.com/2018-03-23/v_ekaterinburge_k_chm_2018_poyavyatsya_informacionnye_punkty_za_3_1_mln_rubley", "2018.03.23"));
//        sourceList.add( new NewsElement("«Госкорпорации — мера временная, от нее надо уходить» Член-корреспондент РАН о том, что такое экономика XXI века, и о курсе России при Путине ", " 21.03.2018 ", "20:39", "https://img.znak.com/1899718.jpg" ,"https://znak.com/2018-03-21/chlen_korrespondent_ran_o_tom_chto_takoe_ekonomika_xxi_veka_i_kurse_rossii_pri_putine", "2018.03.21"));
//        sourceList.add( new NewsElement("Суд принял решение убрать полусферу с городского пруда Екатеринбурга ", " 21.03.2018 ", "11:48", "https://img.znak.com/1898904.jpg" ,"https://znak.com/2018-03-21/sud_prinyal_reshenie_ubrat_polusferu_s_gorodskogo_pruda_ekaterinburga", "2018.03.21"));
//        sourceList.add( new NewsElement("На дату сноса телебашни Екатеринбурга билеты на шоу в цирке выкупила частная организация ", " 15.03.2018 ", "12:43", "https://img.znak.com/1887167.jpg" ,"https://znak.com/2018-03-15/na_datu_snosa_telebashni_ekaterinburga_bilety_na_shou_v_cirke_vykupila_chastnaya_organizaciya", "2018.03.15"));
//        sourceList.add( new NewsElement("Челябинск Архивы онлайн Челябинцы получили доступ к уникальным архивным документам Президентской библиотеки ", " 14.03.2018 ", "12:34", "https://img.znak.com/1885074.jpg" ,"https://znak.com/2018-03-14/chelyabincy_poluchili_dostup_k_unikalnyh_arhivnym_dokumentam_prezidentskoy_biblioteki", "2018.03.14"));
//        sourceList.add( new NewsElement("Названа новая ориентировочная дата сноса екатеринбургской телебашни ", " 13.03.2018 ", "13:10", "https://img.znak.com/1882993.jpg" ,"https://znak.com/2018-03-13/nazvana_novaya_orientirovochnaya_data_snosa_ekaterinburgskoy_telebashni", "2018.03.13"));
//        sourceList.add( new NewsElement("В Екатеринбурге болельщикам ЧМ-2018 предложат туры с посещением баров и пивных ", " 12.03.2018 ", "14:31", "https://img.znak.com/1881151.jpg" ,"https://znak.com/2018-03-12/v_ekaterinburge_bolelchikam_chm_2018_predlozhat_tury_s_posecheniem_barov_i_pivnyh", "2018.03.12"));
//        sourceList.add( new NewsElement("«Демократия — это когда человек не боится» Михаил Зыгарь о национальной идее России, имперских амбициях и президентской кампании ", " 05.03.2018 ", "6:00", "https://img.znak.com/1866260.jpg" ,"https://znak.com/2018-03-05/mihail_zygar_o_nacionalnoy_idee_rossii_imperskih_ambiciyah_i_prezidentskoy_kampanii", "2018.03.05"));
//        sourceList.add( new NewsElement("В Екатеринбурге появились сквер Бабыкина и парк Архипова ", " 03.03.2018 ", "15:48", "https://img.znak.com/1866368.jpg" ,"https://znak.com/2018-03-03/skver_babykina_i_park_arhipova_poyavilis_v_ekaterinburge", "2018.03.03"));
//        sourceList.add( new NewsElement("Ранее в полусферу не удавалось попасть практически никому «Такой же есть еще один в области, у богатого товарища» Камин, умывальник и кухня: Znak.com попал в полусферу на городском пруде Екатеринбурга ", " 01.03.2018 ", "19:23", "https://img.znak.com/1863932.jpg" ,"https://znak.com/2018-03-01/kamin_umyvalnik_i_kuhnya_znak_com_popal_v_polusferu_na_gorodskom_prude_ekaterinburga", "2018.03.01"));
//        sourceList.add( new NewsElement("В Екатеринбурге музей конструктивизма вынужден отдать часть своих площадей швейному цеху ", " 26.02.2018 ", "17:54", "https://img.znak.com/1856630.jpg" ,"https://znak.com/2018-02-26/v_ekaterinburge_muzey_konstruktivizma_vynuzhden_otdat_chast_svoih_plochadey_shveynomu_cehu", "2018.02.26"));
//        sourceList.add( new NewsElement("«Яндекс» убрал с карт недостроенную телебашню в Екатеринбурге, не дождавшись ее сноса ", " 21.02.2018 ", "17:39", "https://img.znak.com/1848002.jpg" ,"https://znak.com/2018-02-21/yandeks_ubral_s_kart_nedostroennuyu_telebashnyu_v_ekaterinburge_ne_dozhdavshis_ee_snosa", "2018.02.21"));
//        sourceList.add( new NewsElement("«Полиэтилен, грязь». Полусферу на пруду Екатеринбурга начали переодевать к ЧМ по футболу ", " 16.02.2018 ", "10:16", "https://img.znak.com/1838150.jpg" ,"https://znak.com/2018-02-16/polietilen_gryaz_polusferu_na_prudu_ekaterinburga_nachali_pereodevat_k_chm_po_futbolu", "2018.02.16"));
//        sourceList.add( new NewsElement("Екатеринбуржцы передадут Путину подписи в защиту недостроенной телебашни ", " 15.02.2018 ", "14:24", "https://img.znak.com/1836503.jpg" ,"https://znak.com/2018-02-15/ekaterinburzhcy_peredadut_putinu_podpisi_v_zachitu_nedostroennoy_telebashni", "2018.02.15"));
//        sourceList.add( new NewsElement("«Там должен быть музей футбола» Представители зеленой полусферы на пруду Екатеринбурга: у нас просили взятку ", " 15.02.2018 ", "10:50", "https://img.znak.com/1836053.jpg" ,"https://znak.com/2018-02-15/predstaviteli_zelenoy_polusfery_na_prudu_ekaterinburga_u_nas_prosili_vzyatku", "2018.02.15"));
//        sourceList.add( new NewsElement("Работникам музея «Россия — моя история» в Екатеринбурге озвучили дату сноса телебашни ", " 14.02.2018 ", "17:30", "https://img.znak.com/1834809.jpg" ,"https://znak.com/2018-02-14/rabotnikam_muzeya_rossiya_moya_istoriya_v_ekaterinburge_ozvuchili_datu_snosa_telebashni", "2018.02.14"));
//        sourceList.add( new NewsElement("Сейчас корпуса бывшего завода пустуют Элитный дом попал в историю Строительство на месте старого завода в центре Екатеринбурга замедлится из-за находок археологов ", " 05.02.2018 ", "20:29", "https://img.znak.com/1817720.jpg" ,"https://znak.com/2018-02-05/uralskiy_zastroychik_ne_smozhet_bystro_nachat_proekt_u_rezidencii_gubernatora_iz_za_nahodok_arheolog", "2018.02.05"));
//        sourceList.add( new NewsElement("Два екатеринбуржца приняли участие в записи программы «Пуcть говорят» о Высоцком ", " 25.01.2018 ", "11:23", "https://img.znak.com/1796247.jpg" ,"https://znak.com/2018-01-25/dva_ekaterinburzhca_prinyali_uchastie_v_zapisi_programmy_put_govoryat_o_vysockom", "2018.01.25"));
//        sourceList.add( new NewsElement("Челябинск Учатся всю жизнь Исторический музей открывает секреты работы реставраторов, устраивая публичные встречи с мастерами ", " 23.01.2018 ", "12:00", "https://img.znak.com/1791104.jpg" ,"https://znak.com/2018-01-23/istoricheskiy_muzey_otkryvaet_sekrety_raboty_restavratorov_ustraivaya_publichnye_vstrechi_s_masteram", "2018.01.23"));
//        sourceList.add( new NewsElement("Командировок в Россию не значится Канал Discovery опроверг информацию о том, что снимает фильм о телебашне в Екатеринбурге ", " 18.01.2018 ", "13:03", "https://img.znak.com/1783462.jpg" ,"https://znak.com/2018-01-18/kanal_discovery_oproverg_informaciyu_o_tom_chto_snimaet_film_o_telebashne_v_ekaterinburge", "2018.01.18"));
//        sourceList.add( new NewsElement("Куйвашев рассказал, кто должен оценивать итоги работы Ройзмана ", " 16.01.2018 ", "13:20", "https://img.znak.com/1779310.jpg" ,"https://znak.com/2018-01-16/kuyvashev_rasskazal_kto_dolzhen_ocenivat_itogi_raboty_royzmana", "2018.01.16"));
//        sourceList.add( new NewsElement("Госэкспертиза одобрила строительство в Екатеринбурге новой телебашни ", " 11.01.2018 ", "19:07", "https://img.znak.com/1771404.jpg" ,"https://znak.com/2018-01-11/gosekspertiza_odobrila_stroitelstvo_v_ekaterinburge_novoy_telebashni", "2018.01.11"));
//        sourceList.add( new NewsElement("В Екатеринбурге скончался хранитель наследия Эрнста Неизвестного ", " 30.12.2017 ", "10:50", "https://img.znak.com/1752830.jpg" ,"https://znak.com/2017-12-30/v_ekaterinburge_skonchalsya_hranitel_naslediya_ernsta_neizvestnogo", "2017.12.30"));
//        sourceList.add( new NewsElement("Новый год и Рождество в Ельцин Центре: самые интересные мероприятия января 2018 года ", " 29.12.2017 ", "18:03", "https://img.znak.com/1751708.jpg" ,"https://znak.com/2017-12-29/novyy_god_i_rozhdestvo_v_elcin_centre_samye_interesnye_meropriyatiya_yanvarya_2018_goda", "2017.12.29"));
//        sourceList.add( new NewsElement("«Это будет вторая „Сортировка“ или хуже» Инженер-физик: снос телебашни в Екатеринбурге может разрушить здания, метро и два моста ", " 12.12.2017 ", "19:30", "https://img.znak.com/1716460.jpg" ,"https://znak.com/2017-12-12/inzhener_fizik_padenie_telebashni_v_ekaterinburge_mozhet_razrushit_zdaniya_metro_dva_mosta", "2017.12.12"));
//        sourceList.add( new NewsElement("«Медная горка» - проект, на основе которого разрабатывают концепцию будущей застройки «Давайте их обломаем сегодня? Вам ничего за это не будет» Несмотря на протесты, проект УГМК в центре Екатеринбурга прошел публичные слушания ", " 11.12.2017 ", "19:05", "https://img.znak.com/1714393.jpg" ,"https://znak.com/2017-12-11/nesmotrya_na_protesty_proekt_ugmk_v_centre_ekaterinburga_proshel_publichnye_slushaniya", "2017.12.11"));
//        sourceList.add( new NewsElement("Дума Екатеринбурга наградила мэрию медалями в честь своего дня рождения ", " 08.12.2017 ", "17:29", "https://img.znak.com/1709353.jpg" ,"https://znak.com/2017-12-08/duma_ekaterinburga_nagradila_meriyu_medalyami_v_chest_sobstvennogo_dnya_rozhdeniya", "2017.12.08"));
//        sourceList.add( new NewsElement("Спорный проект компании, построившей на Урале Музей истории РФ, прошел публичные слушания ", " 07.12.2017 ", "13:35", "https://img.znak.com/1706832.jpg" ,"https://znak.com/2017-12-07/spornyy_proekt_kompanii_postroivshey_na_urale_muzey_istorii_rf_proshel_publichnye_slushaniya", "2017.12.07"));
//        sourceList.add( new NewsElement("Скоро это маленькое административное здание превратится в 24-этажную высотку Чудесное превращение одноэтажки в высотку В Екатеринбурге компания, построившая музей истории России, затеяла спорный проект ", " 05.12.2017 ", "15:47", "https://img.znak.com/1703046.jpg" ,"https://znak.com/2017-12-05/v_ekaterinburge_kompaniya_postroivshaya_muzey_istorii_rf_zateyala_spornyy_proekt", "2017.12.05"));
//        sourceList.add( new NewsElement("Зима в Ельцин Центре: самые интересные мероприятия декабря ", " 30.11.2017 ", "17:50", "https://img.znak.com/1694589.jpg" ,"https://znak.com/2017-11-30/zima_v_elcin_centre_samye_interesnye_meropriyatiya_dekabrya", "2017.11.30"));
//        sourceList.add( new NewsElement("Сторонники теории заговоров считают, что организатор убийства Царской семьи Свердлов действовал по приказу американских банкиров еврейского происхождения «По приказу тайных сил» Как сторонники версии о ритуальном убийстве представляют себе гибель Николая II ", " 29.11.2017 ", "17:40", "https://img.znak.com/1692427.jpg" ,"https://znak.com/2017-11-29/kak_storonniki_versii_o_ritualnom_ubiystve_predstavlyayut_sebe_gibel_nikolaya_ii", "2017.11.29"));
//        sourceList.add( new NewsElement("В Екатеринбурге строят парк площадью 50 гектаров: c горами, аттракционами и кремлем ", " 29.11.2017 ", "17:11", "https://img.znak.com/1692472.jpg" ,"https://znak.com/2017-11-29/v_ekaterinburge_stroyat_park_plochadyu_50_gektarov_c_gorami_attrakcionami_i_kremlem", "2017.11.29"));
//        sourceList.add( new NewsElement("«Откармливали икрой. Провожали с оркестром и шампанским» В Ельцин Центре включились в дискуссию по докладу школьника в бундестаге о пленных немцах ", " 25.11.2017 ", "6:29", "https://img.znak.com/1684563.jpg" ,"https://znak.com/2017-11-25/v_elcin_centre_vklyuchilis_v_diskussiyu_po_dokladu_shkolnika_v_bundestage_o_plennyh_nemcah", "2017.11.25"));
//        sourceList.add( new NewsElement("Ельцин Центр опубликовал программу нового фестиваля «Остров 90-х» ", " 21.11.2017 ", "10:15", "https://img.znak.com/1676796.jpg" ,"https://znak.com/2017-11-21/elcin_centr_opublikoval_programmu_novogo_festivalya_ostrov_90_h", "2017.11.21"));
//        sourceList.add( new NewsElement("Михаил Федотов «Прошу обратить внимание отрядов!» Глава СПЧ предложил привлечь поисковиков к изучению репрессий. Начнут с Екатеринбурга ", " 20.11.2017 ", "21:27", "https://img.znak.com/1676057.jpg" ,"https://znak.com/2017-11-20/glava_spch_predlozhil_privlech_poiskovikov_k_izucheniyu_repressiy_nachnut_s_ekaterinburga", "2017.11.20"));
//        sourceList.add( new NewsElement("«Всем попавшим под пресс тоталитарного режима посвящается» В Екатеринбурге открыли второй в России памятник «Маски Скорби» Эрнста Неизвестного ", " 20.11.2017 ", "14:19", "https://img.znak.com/1675197.jpg" ,"https://znak.com/2017-11-20/v_ekaterinburge_otkryli_vtoroy_v_rossii_pamyatnik_maski_skorbi_ernsta_neizvestnogo", "2017.11.20"));
//        sourceList.add( new NewsElement("В Екатеринбурге Евгений Куйвашев встретился с новым послом США Джоном Хантсманом ", " 20.11.2017 ", "10:25", "https://img.znak.com/1674754.jpg" ,"https://znak.com/2017-11-20/v_ekaterinburge_evgeniy_kuyvashev_vstretilsya_s_novym_poslom_ssha_dzhonom_hantsmanom", "2017.11.20"));
//        sourceList.add( new NewsElement("В приезжает новый посол США Хантсман. С ним встретится губернатор ", " 19.11.2017 ", "14:20", "https://img.znak.com/1672539.jpg" ,"https://znak.com/2017-11-19/gubernator_kuyvashev_vstrechaetsya_s_novym_poslom_ssha_v_rossii_hantsmanom", "2017.11.19"));
//        sourceList.add( new NewsElement("Центр «Эрмитаж-Урал» появится в Екатеринбурге к 75-летию Великой Победы ", " 17.11.2017 ", "14:18", "https://img.znak.com/1670360.jpg" ,"https://znak.com/2017-11-17/centr_ermitazh_ural_poyavitsya_v_ekaterinburge_k_75_letiyu_pobedy_v_vov", "2017.11.17"));
//        sourceList.add( new NewsElement("Вице-спикер думы Виктор Тестов и хор «Эти люди приходят и целыми днями думают» Дума Екатеринбурга отметила юбилей под White Stripes и сценку из 1915 года ", " 16.11.2017 ", "19:50", "https://img.znak.com/1668995.jpg" ,"https://znak.com/2017-11-16/duma_ekaterinburga_otmetila_yubiley_pod_white_stripes_i_scenki_iz_1915_goda", "2017.11.16"));
//        sourceList.add( new NewsElement("«Маски» готовят к установке «Вы там, на Урале, все сумасшедшие» Как «Маски скорби» спустя 30 лет наконец нашли свое место в Екатеринбурге ", " 13.11.2017 ", "21:27", "https://img.znak.com/1663031.jpg" ,"https://znak.com/2017-11-13/kak_maski_skorbi_spustya_30_let_nakonec_nashli_svoe_mesto_v_ekaterinburge", "2017.11.13"));
//        sourceList.add( new NewsElement("В приехал испанский архитектор: дорабатывать проект ЖК на Широкой речке ", " 02.11.2017 ", "13:40", "https://img.znak.com/1643880.jpg" ,"https://znak.com/2017-11-02/v_ekaterinburg_priehal_ispanskiy_arhitektor_dorabatyvat_proekt_zhk_na_shirokoy_rechke", "2017.11.02"));
//        sourceList.add( new NewsElement("Минкульт: 86 млн рублей на музей в Алапаевске выделяют к визиту патриарха Кирилла ", " 02.11.2017 ", "11:11", "https://yt3.ggpht.com/a-/AJLlDp3w3Ok_TD46pLqIlFB7_TbbwUHQ4D867hKRhQ=s900-mo-c-c0xffffffff-rj-k-no" ,"https://znak.com/2017-11-02/minkult_86_mln_rubley_na_muzey_v_alapaevske_vydelyayut_k_vizitu_patriarha_kirilla", "2017.11.02"));
//        sourceList.add( new NewsElement("Якоб открыл выставку в честь юбилея гордумы: два века прошло — ничего не изменилось ", " 01.11.2017 ", "19:11", "https://img.znak.com/1642635.jpg" ,"https://znak.com/2017-11-01/yakob_otkryl_vystavku_v_chest_yubileya_gordumy_dva_veka_proshlo_nichego_ne_izmenilos", "2017.11.01"));
//        sourceList.add( new NewsElement("Сегодня в Музее истории Екатеринбурга городские депутаты устроят фуршет ", " 01.11.2017 ", "10:56", "https://img.znak.com/1641689.jpg" ,"https://znak.com/2017-11-01/segodnya_v_muzee_istorii_ekaterinburga_gorodskie_deputaty_ustroyat_furshet_za_1_mln_rubley", "2017.11.01"));
//        sourceList.add( new NewsElement("Ноябрь в Ельцин Центре: самые интересные мероприятия ", " 31.10.2017 ", "19:07", "https://img.znak.com/1637324.jpg" ,"https://znak.com/2017-10-31/noyabr_v_elcin_centre_samye_interesnye_meropriyatiya", "2017.10.31"));
//        sourceList.add( new NewsElement("У жителей Екатеринбурга есть запрос на новый качественный парк. Но власть их пока не слышит. В мэрии Екатеринбурга говорят, что Шадриным довольны «Это драма. Очевидно, что это надо менять» Алиса Прудникова — о том, чем бы мог стать ЦПКиО для Екатеринбурга ", " 31.10.2017 ", "14:55", "https://img.znak.com/1636816.jpg" ,"https://znak.com/2017-10-31/alisa_prudnikova_o_tom_chem_by_mog_stat_cpkio_dlya_ekaterinburga", "2017.10.31"));
//        sourceList.add( new NewsElement("«Такие вещи помнить больно и унизительно, но их надо помнить» На Урале на месте захоронений жертв репрессий установили фигуры погибших во время террора ", " 31.10.2017 ", "10:44", "https://img.znak.com/1636361.jpg" ,"https://znak.com/2017-10-31/na_urale_na_meste_zahoroneniy_zhertv_repressiy_ustanovili_figury_pogibshih_vo_vremya_terrora", "2017.10.31"));
//        sourceList.add( new NewsElement("В центре Екатеринбурга «Мемориал» проведет акцию памяти жертв политических репрессий ", " 30.10.2017 ", "14:33", "https://img.znak.com/1635035.jpg" ,"https://znak.com/2017-10-30/v_centre_ekaterinburga_memorial_provedet_akciyu_pamyati_zhertv_politicheskih_repressiy", "2017.10.30"));
//        sourceList.add( new NewsElement("«Пусть Шнуров придумает песню» Заявочный комитет ЭКСПО-2025 попросил у журналистов Екатеринбурга поддержки ", " 25.10.2017 ", "16:09", "https://img.znak.com/1627774.jpg" ,"https://znak.com/2017-10-25/zayavochnyy_komitet_ekspo_2025_poprosil_u_zhurnalistov_ekaterinburga_podderzhki", "2017.10.25"));
//        sourceList.add( new NewsElement("Франц Мали с лучшей бригадой хлебопеков завода «Автомат» «Провел на хлебозаводе большую диверсионную работу» Книга памяти. Как в 1938-м по доносу отправили в лагерь лучшего пекаря Свердловска ", " 24.10.2017 ", "19:54", "https://img.znak.com/1626462.jpg" ,"https://znak.com/2017-10-24/kniga_pamyati_kak_v_1938_m_po_donosu_otpravili_v_lager_glavnogo_pekarya_sverdlovska", "2017.10.24"));
//        sourceList.add( new NewsElement("«Это наш праздник». Тунгусов позвал министров на крестный ход и шествие 4 ноября ", " 18.10.2017 ", "10:36", "https://img.znak.com/1616815.jpg" ,"https://znak.com/2017-10-18/eto_nash_prazdnik_tungusov_pozval_ministrov_na_krestnyy_hod_i_shestvie_4_noyabrya", "2017.10.18"));
//        sourceList.add( new NewsElement("Мединский пообещал рассмотреть вопрос с выделением 400 млн рублей на уральский «Эрмитаж» ", " 16.10.2017 ", "16:45", "https://img.znak.com/1613896.jpg" ,"https://znak.com/2017-10-16/medinskiy_poobechal_rassmotret_vopros_s_vydeleniem_400_mln_rubley_na_uralskiy_ermitazh", "2017.10.16"));
//        sourceList.add( new NewsElement("Свердловское минспорта после ЧМ-2018 может переехать на Центральный стадион ", " 11.10.2017 ", "16:35", "https://img.znak.com/1606638.jpg" ,"https://znak.com/2017-10-11/sverdlovskoe_minsporta_posle_chm_2018_mozhet_pereehat_na_centralnyy_stadion", "2017.10.11"));
//        sourceList.add( new NewsElement("Движение «Сорок Сороков» рассказало об акциях против «Матильды» в 50 городах России ", " 08.10.2017 ", "18:53", "https://img.znak.com/1601892.jpg" ,"https://znak.com/2017-10-08/dvizhenie_sorok_sorokov_rasskazalo_ob_akciyah_protiv_matildy_v_50_gorodah_rossii", "2017.10.08"));
//        sourceList.add( new NewsElement("«Путин искренне понимает, что он — последняя скрепа» Дмитрий Быков — о новых и будущих книгах, разговорах с властью и сверхчеловеке ", " 07.10.2017 ", "15:10", "https://img.znak.com/1600398.jpg" ,"https://znak.com/2017-10-07/dmitriy_bykov_o_novyh_i_buduchih_knigah_razgovorah_s_vlastyu_i_sverhcheloveke", "2017.10.07"));
//        sourceList.add( new NewsElement("Выходные в Ельцин Центре Ради чего стоит приехать в в октябре. Самые интересные мероприятия ", " 03.10.2017 ", "17:10", "https://img.znak.com/1589161.jpg" ,"https://znak.com/2017-10-03/radi_chego_stoit_priehat_v_oktyabre_v_ekaterinburg_samye_interesnye_meropriyatiya", "2017.10.03"));
//        sourceList.add( new NewsElement("Николай II с дочерьми Ольгой, Анастасией и Татьяной (Тобольск, зима 1917 года) «Сейчас я раскрою некоторые секреты» Следователь Соловьев в Екатеринбурге рассказал подробности дела об убийстве Николая II ", " 02.10.2017 ", "19:37", "https://img.znak.com/1592309.jpg" ,"https://znak.com/2017-10-02/sledovatel_solovev_v_ekaterinburge_rasskazal_podrobnosti_dela_ob_ubiystve_nikolaya_ii", "2017.10.02"));
//        sourceList.add( new NewsElement("Венедиктов: ДНК останков Александра III и Николая II совпадают ", " 30.09.2017 ", "21:01", "https://img.znak.com/1590064.jpg" ,"https://znak.com/2017-09-30/venediktov_dnk_ostankov_aleksandra_iii_i_nikolaya_ii_sovpali", "2017.09.30"));
//        sourceList.add( new NewsElement("Восстановленный исторический фонтан из усадьбы купца Степанова, 30 июня 2016 года Челябинск История чугунного вазона Тайны старого Челябинска: купеческий фонтан ", " 29.09.2017 ", "15:07", "https://img.znak.com/1588111.jpg" ,"https://znak.com/2017-09-29/tayny_starogo_chelyabinska_kupecheskiy_fontan", "2017.09.29"));
//        sourceList.add( new NewsElement("Челябинск Исторический музей Южного Урала откроет выставку о судьбе царской семьи ", " 29.09.2017 ", "9:52", "https://img.znak.com/1588135.jpg" ,"https://znak.com/2017-09-29/istoricheskiy_muzey_yuzhnogo_urala_otkroet_vystavku_o_sudbe_carskoy_semi", "2017.09.29"));
//        sourceList.add( new NewsElement("В эксперт приехал на Школу главного архитектора «Уничтожение телебашни — это акт бессилия, слабости» Доктор архитектуры Андрей Боков — о будущем Екатеринбурга, храме на воде и сносе телебашни ", " 19.09.2017 ", "19:01", "https://img.znak.com/1576021.jpg" ,"https://znak.com/2017-09-19/doktor_arhitektury_o_buduchem_ekaterinburga_hrame_na_vode_i_snose_telebashni", "2017.09.19"));
//        sourceList.add( new NewsElement("Тусовщик, няня для пса, модель для Инстаграма. Топ любопытных вакансий для уральцев ", " 18.09.2017 ", "10:48", "https://img.znak.com/1573630.jpg" ,"https://znak.com/2017-09-18/tusovchik_nyanya_dlya_psa_model_dlya_instagrama_top_lyubopytnyh_vakansiy_dlya_uralcev", "2017.09.18"));
//        sourceList.add( new NewsElement("В музее истории, открытом в Екатеринбурге духовником Путина, наградили самые трезвые села ", " 08.09.2017 ", "18:24", "https://img.znak.com/1561878.jpg" ,"https://znak.com/2017-09-08/v_muzee_istorii_otkrytom_v_ekaterinburge_duhovnikom_putina_nagradili_samye_trezvye_sela", "2017.09.08"));
//        sourceList.add( new NewsElement("«Это не совсем то, о чем мы мечтали для России» Вдова Владимира Высоцкого Марина Влади — о своем муже, государстве и патриотизме. Интервью ", " 05.09.2017 ", "21:10", "https://img.znak.com/1557856.jpg" ,"https://znak.com/2017-09-05/vdova_vladimira_vysockogo_marina_vladi_o_svoem_muzhe_gosudarstve_i_patriotizme_intervyu", "2017.09.05"));
//        sourceList.add( new NewsElement("«Много резких поворотов, темных закоулков» Znak.com сходил на экскурсию по парку истории России, придуманному духовником Путина ", " 01.09.2017 ", "19:06", "https://tps://img.znak.com/1553362.jpg" ,"https://znak.com/2017-09-01/znak_com_shodil_na_ekskursiyu_po_parku_istorii_rossii_pridumannomu_duhovnikom_putina", "2017.09.01"));
//        sourceList.add( new NewsElement("Выходные в Екатеринбурге Что интересного в Ельцин Центре в сентябре ", " 30.08.2017 ", "20:25", "https://img.znak.com/1550620.jpg" ,"https://znak.com/2017-08-30/chto_interesnogo_v_elcin_centre_v_sentyabre", "2017.08.30"));
//        sourceList.add( new NewsElement("Челябинск «Лучше что-то делать, чем сдрейфить» Художница Алиса Горшенина — о том, как из крохотной деревни попасть на крупнейшие выставки ", " 25.08.2017 ", "11:12", "https://img.znak.com/1543895.jpg" ,"https://znak.com/2017-08-25/hudozhnica_alisa_gorshenina_o_tom_kak_iz_krohotnoy_derevni_popast_na_krupneyshie_vystavki", "2017.08.25"));
//        sourceList.add( new NewsElement("Челябинск В Челябинске впервые пройдет Уральский театральный форум ", " 23.08.2017 ", "14:27", "https://img.znak.com/1541432.jpg" ,"https://znak.com/2017-08-23/v_chelyabinske_vpervye_proydet_uralskiy_teatralnyy_forum", "2017.08.23"));
//        sourceList.add( new NewsElement("Челябинск Обладание произведением искусства — это обладание идеей В Челябинске открылась первая коммерческая выставка современного европейского искусства ", " 23.08.2017 ", "10:53", "https://img.znak.com/1541081.jpg" ,"https://znak.com/2017-08-23/v_chelyabinske_otkrylas_pervaya_kommercheskaya_vystavka_sovremennogo_evropeyskogo_iskusstva", "2017.08.23"));
//        sourceList.add( new NewsElement("Самые интересные мероприятия Гид по Дню города — 2017 в Екатеринбурге ", " 17.08.2017 ", "18:21", "https://img.znak.com/1534044.jpg" ,"https://znak.com/2017-08-17/gid_po_dnyu_goroda___2017_v_ekaterinburge", "2017.08.17"));
//        sourceList.add( new NewsElement("Челябинск «Мне недостаточно видеть меч как красивый предмет, мне нужно чувствовать его мощь» В Челябинске проходит цикл лекций по японской культуре и боевым искусствам ", " 17.08.2017 ", "14:07", "https://img.znak.com/1533636.jpg" ,"https://znak.com/2017-08-17/v_chelyabinske_prohodit_cikl_lekciy_po_yaponskoy_kulture_i_boevym_iskusstvam", "2017.08.17"));
//        sourceList.add( new NewsElement("Слава PTRK повторил в Москве инсталляцию из костылей «Страна возможностей» ", " 14.08.2017 ", "15:56", "https://tps://img.znak.com/1529586.jpg" ,"https://znak.com/2017-08-14/slava_ptrk_povtoril_v_moskve_installyaciyu_iz_kostyley_strana_vozmozhnostey", "2017.08.14"));
//        sourceList.add( new NewsElement("В Екатеринбурге в канун 100-летия Октября запускают новое революционное подполье ", " 11.08.2017 ", "12:15", "https://img.znak.com/1526256.jpg" ,"https://znak.com/2017-08-11/v_ekaterinburge_v_kanun_100_letiya_oktyabrya_zapuskayut_novoe_revolyucionnoe_podpole", "2017.08.11"));
//        sourceList.add( new NewsElement("В Верхотурье на день. Что увидят туристы в «Духовной столице Урала» ", " 08.08.2017 ", "10:20", "https://img.znak.com/1521023.jpg" ,"https://znak.com/2017-08-08/v_verhoture_na_den_chto_uvidyat_turisty_v_duhovnoy_stolice_urala", "2017.08.08"));
//        sourceList.add( new NewsElement("Музей Екатеринбурга раскрыл секрет памятника соратнику Ленина, расстрелявшему Николая II ", " 04.08.2017 ", "18:00", "https://img.znak.com/1518146.jpg" ,"https://znak.com/2017-08-04/muzey_ekaterinburga_raskryl_sekret_pamyatnika_soratniku_lenina_rasstrelyavshego_nikolaya_ii", "2017.08.04"));
//        sourceList.add( new NewsElement("Челябинск Открытие на изломе страны, на границе эпох Один из первооткрывателей Аркаима — о значении древнего поселения в прошлом и будущем Урала ", " 04.08.2017 ", "13:48", "https://img.znak.com/1515577.jpg" ,"https://znak.com/2017-08-04/odin_iz_pervootkryvateley_arkaima_o_znachenii_drevnego_poseleniya_v_proshlom_i_buduchem_urala", "2017.08.04"));
//        sourceList.add( new NewsElement("Челябинск Выходные в Екатеринбурге Ельцин Центр: что интересного в августе ", " 02.08.2017 ", "19:00", "https://tps://img.znak.com/1515354.jpg" ,"https://znak.com/2017-08-02/elcin_centr_chto_interesnogo_v_avguste", "2017.08.02"));
//        sourceList.add( new NewsElement("Экспозиция нового музея Екатеринбурга за 350 млн: заводы, храмы, Симеон Верхотурский ", " 01.08.2017 ", "18:43", "https://img.znak.com/1513978.jpg" ,"https://znak.com/2017-08-01/ekspoziciya_novogo_muzeya_ekaterinburga_za_350_mln_zavody_hramy_simeon_verhoturskiy", "2017.08.01"));
//        sourceList.add( new NewsElement("В едет пресс-секретарь Ельцина, чтобы научить жителей эффективно вести диалог ", " 01.08.2017 ", "17:02", "https://img.znak.com/1513788.jpg" ,"https://znak.com/2017-08-01/v_ekaterinburg_edet_press_sekretar_elcina_chtoby_nauchit_zhiteley_effektivno_vesti_dialog", "2017.08.01"));
//        sourceList.add( new NewsElement("«Конец карьеры должен был быть другим» Николай Косарев — об уголовной подоплеке своего ухода с поста ректора Горного университета ", " 31.07.2017 ", "19:42", "https://img.znak.com/1512653.jpg" ,"https://znak.com/2017-07-31/nikolay_kosarev_ob_ugolovnoy_podopleke_svoego_uhoda_s_posta_rektora_gornogo_universiteta", "2017.07.31"));
        sourceList.add( new NewsElement("В приедет Марина Влади, которой вручат премию «Жёны декабристов ХХ века» ", " 25.07.2017 ", "12:53", "https://img.znak.com/1504042.jpg" ,"https://znak.com/2017-07-25/v_ekaterinburg_priedet_marina_vladi_kotoroy_vruchat_premiyu_zheny_dekabristov_hh_veka", "2017.07.25"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_parser, container, false);

        if(savedInstanceState==null) {

            if(!sl) {
                fullSourceList();
                sl=true;
            }
            NewThread newThread = new NewThread();
            newThread.execute();
            RecyclerView recyclerView = view.findViewById(R.id.rv_list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new MyRecyclerViewAdapter(view.getContext(), newsList);
            recyclerView.setAdapter(adapter);
        }
        return view;

    }

    public class NewThread extends AsyncTask<String, Void, String> {

        String cleaner(String a){
            String source = a;
            String result = "";
            ArrayList<String> arr = new ArrayList<>();
            arr.add(source);

            for(String retrival: source.split(" ")){
                arr.add(retrival);

            }
            arr.remove(0);
            Iterator<String> iterator = arr.iterator();
            while (iterator.hasNext()) {
                String string = iterator.next();
                if (string.equals("Россия") || string.equals("Екатеринбург")) {
                    iterator.remove();
                }else{
                    result += string+" ";
                }
            }
            return result;
        }

        String url_interpretator(String src){
            try {


                String result = "";
                String interval = "";

                char[] morph = src.toCharArray();
                if (morph.length > 0) {
                    morph[0] = ' ';
                    morph[1] = ' ';
                } else {
                    return "https://yt3.ggpht.com/a-/AJLlDp3w3Ok_TD46pLqIlFB7_TbbwUHQ4D867hKRhQ=s900-mo-c-c0xffffffff-rj-k-no";
                }

                for (char a : morph) {
                    if (a == ' ') {
                        continue;
                    } else {
                        interval = interval + a;
                    }
                }

                result = "https://" + interval;

                return result;
            }catch (Exception e){
                return "https://yt3.ggpht.com/a-/AJLlDp3w3Ok_TD46pLqIlFB7_TbbwUHQ4D867hKRhQ=s900-mo-c-c0xffffffff-rj-k-no";
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected  String doInBackground(String... arg) {
            Document doc;
            String  newsText,  newsDate,  newsTime,  newsPicURL,  newsLink;

            try{
                doc= Jsoup.connect("https://www.znak.com/?%D0%B5%D0%BA%D0%B0%D1%82%D0%B5%D1%80%D0%B8%D0%BD%D0%B1%D1%83%D1%80%D0%B3%20%D0%BC%D1%83%D0%B7%D0%B5").get();

                content = doc.select(".pub");

                int link_counter = 0;

                newsList.clear();
                for(Element contents: content){
                    Log.d("Тред", "Добавляю");

                    String linkID = doc.getElementsByClass("pub").get(link_counter).attr("href");

                    String region = doc.getElementsByClass("region").get(link_counter).text();

                    String time = doc.getElementsByTag("time").get(link_counter).attr("datetime");

                    String sourceYear;

                    DateAndTime dat = new DateAndTime(time);
                    newsText=cleaner(contents.text());
                    newsDate=" "+dat.getYear()+" ";
                    newsTime=dat.getTime();

                   if(newsText.equals(sourceList.get(0).getNewsText())) {
                        publishProgress();
                       break;
                   }

                    Document doc2 = null;
                    try {
                        doc2 = Jsoup.connect("https://www.znak.com" + linkID).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String pic_url = url_interpretator(doc2.getElementsByTag("img").get(1).attr("src"));
                    String readyURL = pic_url;
                    newsPicURL=readyURL;

                    sourceYear=dat.getSourceYear();
                    newsLink="https://znak.com"+linkID;
                    NewsElement newsElement = new NewsElement( newsText,  newsDate,  newsTime,  newsPicURL,  newsLink, sourceYear);
                    newsList.add(newsElement);
                    savedNewsList.add(newsElement);
                    Log.d("Добавлен", newsElement.getNewsText());
                    publishProgress();
                    link_counter++;
                }

            }catch (IOException e){

                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            adapter.notifyDataSetChanged();
        }
    }

    private class DateAndTime{


        private String date;
        private String sourceYear;
        private String year;
        private String time="";
        private char[] timeArr;

        DateAndTime(String date){
            this.date=date;
        }

        public String getSourceYear() {
            return sourceYear;
        }

        String getYear(){


            String[] dateAndTime = date.split("T");
            String[] dateArr = dateAndTime[0].split("-");

            timeArr = dateAndTime[1].toCharArray();
            year = dateArr[2]+"."+dateArr[1]+"."+dateArr[0];
            sourceYear = dateArr[0]+"."+dateArr[1]+"."+dateArr[2];

            return year;

        }


        String getTime(){

            for(int i = 0; i<timeArr.length-4; i++){
                time=time+timeArr[i];
            }

            String intTime = String.valueOf(timeArr[0])+String.valueOf(timeArr[1]);
            int timeValue = Integer.valueOf(intTime)+5;

            if(timeValue>23){
                timeValue=timeValue-24;
                String time = "0"+String.valueOf(timeValue)+timeArr[2]+timeArr[3]+timeArr[4];
                return time;
            }else{
                String time = String.valueOf(timeValue)+timeArr[2]+timeArr[3]+timeArr[4];
                return time;
            }

        }
    }


//    String arrToJson(ArrayList<NewsElement> newsList){
//        String result = gson.toJson(newsList);
//        return result;
//    }
//
//    ArrayList<NewsElement> jsonToArray(String source){
//        ArrayList<NewsElement> result = new ArrayList<>();
//        //result=gson.fromJson(source, ArrayList<NewsElement>().class);
//        try {
//            result= (ArrayList<NewsElement>) jsonObject.get(source);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }



}
