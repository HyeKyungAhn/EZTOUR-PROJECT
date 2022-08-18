//package com.devcamp.eztour.dao.customercenter;
//
//import com.devcamp.eztour.domain.customercenter.CustomerInquiryDto;
//import com.devcamp.eztour.domain.customercenter.CustomerSearchCondition;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.List;
//
//import static org.junit.Assert.assertTrue;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
//public class CustomerInquiryDoaImplTest {
//    @Autowired
//    private CustomerInquiryDao customerInquiryDao;
//
//    @Test
//    public void insertTest() throws Exception {
//        customerInquiryDao.deleteAllCustomerInquiry();
//        CustomerInquiryDto customerInquiryDto = new CustomerInquiryDto();
//        assertTrue(customerInquiryDao.insertCustomerInquiry(customerInquiryDto)==1);
//
//        customerInquiryDto = new CustomerInquiryDto();
//        assertTrue(customerInquiryDao.insertCustomerInquiry(customerInquiryDto)==1);
//        assertTrue(customerInquiryDao.countCustomerInquiry()==2);
//
//        customerInquiryDao.deleteAllCustomerInquiry();
//        customerInquiryDto = new CustomerInquiryDto();
//        assertTrue(customerInquiryDao.insertCustomerInquiry(customerInquiryDto)==1);
//        assertTrue(customerInquiryDao.countCustomerInquiry()==1);
//    }
//
//    @Test
//    public void customerSearchSelectPageTest() throws Exception {
//        customerInquiryDao.deleteAllCustomerInquiry();
//        for (int i = 1; i <= 20; i++) {
//            CustomerInquiryDto customerInquiryDto = new CustomerInquiryDto("title"+i, "asdf", "asdf");
//            customerInquiryDao.insertCustomerInquiry(customerInquiryDto);
//        }
//
//        CustomerSearchCondition csc = new CustomerSearchCondition("test", "test", "title", "T");
//        List<CustomerInquiryDto> list = customerInquiryDao.searchSelectCustomerPage(csc);
//        System.out.println("list = " + list);
//    }
//
//    @Test
//    public void customerSearchSelectPageTest() throws Exception {
//        customerInquiryDao.deleteAllCustomerInquiry();
//        for (int i = 1; i <= 20; i++) {
//            CustomerInquiryDto customerInquiryDto = new CustomerInquiryDto("title"+i, "asdf", "asdf");
//            customerInquiryDao.insertCustomerInquiry(customerInquiryDto);
//        }
//
//        CustomerSearchCondition csc = new CustomerSearchCondition("test", "test", "title", "T");
//        int searchResultCustomerCnt = customerInquiryDao.searchResultCustomerCnt(csc);
//        assertTrue(searchResultCustomerCnt==2);
//    }
//}
