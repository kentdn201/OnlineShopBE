package com.example.springboot.controller;

import com.example.springboot.common.ApiResponse;
import com.example.springboot.dto.order.*;
import com.example.springboot.model.Enum.OrderStatus;
import com.example.springboot.model.Order;
import com.example.springboot.model.OrderProduct;
import com.example.springboot.model.User;
import com.example.springboot.repository.OrderRepository;
import com.example.springboot.repository.UserRepository;
import com.example.springboot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderProductService orderProductService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @GetMapping("/all")
    public List<OrderShowDto> getAllOrders() {
        List<OrderShowDto> orderList = orderService.getAllOrderDto();
        return orderList;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderShowDto>> getOrderByUserId(@PathVariable(name = "userId") Integer userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/don-hang/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetail(@PathVariable(name = "id") Integer id) {
        return ResponseEntity.ok(orderService.getOrdersDetail(id));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse> create(@PathVariable(name = "userId") Integer userId, @RequestBody OrderDto orderDto) throws MessagingException {
        User existUser = userRepository.findByEmail(userRepository.findById(userId).get().getEmail());
        if (existUser == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Your account is not available"), HttpStatus.BAD_REQUEST);
        }
        
        Order order = new Order();
        order.setUser(existUser);
        order.setOrderStatus(OrderStatus.NotDelivery);
        order.setAddress(orderDto.getAddress());
        order.setTypePayment(orderDto.getTypePayment());
        order.setNote(orderDto.getNote());
        order.setPhoneNumber(orderDto.getPhoneNumber());

        orderService.createOrder(order);

        int total = 0;
        String orderToHtml = "";
        List<OrderProduct> orderProducts = new ArrayList<>();
        List<ProductOrderDto> productOrderDtos = orderDto.getProductOrderDtos();

        for (ProductOrderDto productOrderDto : productOrderDtos) {
            total += productOrderDto.getQuantity() * productOrderDto.getPrice();

            orderToHtml += "<tr style=\"border:1px solid black\">\n" +
                    "    <td style=\"border:1px solid black\">" + productService.getProductDetailDtoById(productOrderDto.getId()).getName() + "</td>\n" +
                    "    <td style=\"border:1px solid black\">" + productOrderDto.getQuantity() + "</td>\n" +
                    "    <td style=\"border:1px solid black\">" + productOrderDto.getPrice().intValue() + " VNĐ" + "</td>\n" +
                    "  </tr>\n";

            orderProducts.add
                    (orderProductService.createOrderProduct(
                            new OrderProduct(
                                    order,
                                    productService.findById(productOrderDto.getId()),
                                    productOrderDto.getQuantity(),
                                    productOrderDto.getPrice()))
                    );
        }

        order.setOrderProducts(orderProducts);
        orderService.updateOrder(order);

        String htmlOrder = "<h3>" + "Cảm ơn " + existUser.getFirstName() + " " + existUser.getLastName() + " đã mua hàng tại của hàng của chúng tôi" + "<h3>\n" +
                "<h3>Chúng tôi sẽ liên hệ bạn sớm nhất có thể để xác nhận về đơn hàng</h3>\n" +
                "<p>Thông tin bạn gửi cho chúng tôi như sau:<p> <br/>\n"
                + "<b>Số điện thoại: </b>" + order.getPhoneNumber() + "<br/>\n"
                + "<b>Địa chỉ nhận hàng: </b>" + order.getAddress() + "<br/>\n"
                + "<b>Ghi chú: </b>" + order.getNote() + "<br/>\n"
                + "<b>Tổng giá trị đơn hàng: </b>" + total + " VNĐ" + "<br/>\n"
                + "<h2>Đơn Hàng: </h2>" + "<br/>\n" +
                "<table style=\"width:100%; border:1px solid black\">\n" +
                "  <tr style=\"border:1px solid black\">\n" +
                "    <th style=\"border:1px solid black\">Tên sản phẩm</th>\n" +
                "    <th style=\"border:1px solid black\">Số lượng</th>\n" +
                "    <th style=\"border:1px solid black\">Giá sản phẩm</th>\n" +
                "  </tr>\n"
                + orderToHtml +
                "</table> <br/>\n"
                + "<h3>Chúc bạn có một ngày tốt lành!</h3>";

        emailSenderService.sendEmail(existUser.getEmail(), "Bạn Đã Đặt Hàng Thành Công", htmlOrder);

        return new ResponseEntity<>(new ApiResponse(true, "Order Thành Công"), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}/{userId}")
    public ResponseEntity<ApiResponse> updateOrder(@PathVariable(name = "id") Integer id, @PathVariable(name = "userId") Integer userId, @RequestBody OrderStatusDto orderStatusDto) {
        orderService.updateOrderDto(id, orderStatusDto);
        return new ResponseEntity<>(new ApiResponse(true, "Cập nhật thành công"), HttpStatus.OK);
    }
}
