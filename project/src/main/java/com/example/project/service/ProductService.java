package com.example.project.service;

import com.example.project.entity.*;
import com.example.project.model.CartModel;
import com.example.project.model.OrderModel;
import com.example.project.model.ProductModel;
import com.example.project.model.SkuModel;
import com.example.project.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductR productR;
    @Autowired
    private SkuR skuR;
    @Autowired
    private PriceR priceR;
    @Autowired
    private StockR stockR;
    @Autowired
    private CartR cartR;
    @Autowired
    private OrderR orderR;
    @Autowired
    private ProductionR productionR;
    @Autowired
    private PackingR packingR;
    @Autowired
    private ShippingR shippingR;
    @Autowired
    private DeliveredR deliveredR;
    @Autowired
    private ReturnedR returnedR;

    public String  addProduct(ProductModel productModel) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductCode(productModel.getProductCode());
        productEntity.setProductName(productModel.getProductName());
        productEntity.setDescription(productModel.getDescription());
        productR.save(productEntity);
        return "Product Added Successfully";
    }

    public String addSku(SkuModel skuModel,Integer productCode) {

        Optional<ProductEntity> productEntity = productR.findById(productCode);
        if(productEntity.isPresent()) {
            productEntity.stream().forEach(products -> {

                SkuEntity skuEntity = new SkuEntity();
                skuEntity.setProductEntity(products);
                skuEntity.setSize(skuModel.getSize());
                skuR.save(skuEntity);
                PriceEntity priceEntity = new PriceEntity();
                priceEntity.setSkuCode(skuModel.getSkucode());
                priceEntity.setPrice(skuModel.getPrice());
                priceEntity.setSkuEntity(skuEntity);
                priceR.save(priceEntity);
            });
            return "Sku Added Successfully";
        }
        return "Not Available";
    }
    public String addStock(Integer skuCode,Integer quantity)
    {
        Optional<SkuEntity> stock=skuR.findById(skuCode);
        if(stock.isPresent())
        {
            StockEntity stockEntity=new StockEntity();
            stockEntity.setSkuCode(skuCode);
            stockEntity.setQuantity(quantity);
            stockEntity.setSkuEntity(stock.get());
            stockR.save(stockEntity);
            return "Quantity added for given skuCode";
        }
        else
            return "There is no such skuCode";
    }
    public String addCart(Integer skuCode,Integer quantity)
    {
        Optional<StockEntity> stock=stockR.findById(skuCode);
        if(stock.isPresent())
        {
            if(quantity<=stock.get().getQuantity())
            {
                CartEntity cartEntity=new CartEntity();
                cartEntity.setQuantity(quantity);
                cartEntity.setSkuEntity(stock.get().getSkuEntity());
                cartR.save(cartEntity);
                return "Successfully added to cart";

            }
            return "Out of Stock";
        }
        return "Not Available";
    }
       public List<CartModel> viewCart()
       {
           List<CartEntity> cartEntities=cartR.findAll();
           Double [] i= {0.0};
           List<CartModel> cartModel=cartEntities.stream().map(cart->{
               i[0]+=cart.getQuantity()*cart.getSkuEntity().getPriceEntity().getPrice();
               CartModel cartModel1 =new CartModel();
               cartModel1.setCartCode(cart.getCartCode());
               cartModel1.setProductCode(cart.getSkuEntity().getProductEntity().getProductCode());
               cartModel1.setProductName(cart.getSkuEntity().getProductEntity().getProductName());
               cartModel1.setDescription(cart.getSkuEntity().getProductEntity().getDescription());
               cartModel1.setSize(cart.getSkuEntity().getSize());
               cartModel1.setPrice(cart.getSkuEntity().getPriceEntity().getPrice());
               cartModel1.setSkuCode(cart.getSkuEntity().getSkuCode());
               cartModel1.setQuantity(cart.getQuantity());
               cartModel1.setTotal(i[0]);
               return cartModel1;
           }).collect(Collectors.toList());
           return cartModel;
       }
       public String placeOrder(Integer skuCode,Integer quantity)
       {
           Optional<StockEntity> stock=stockR.findById(skuCode);
           if(stock.isPresent()) {
               StockEntity stockEntity=stock.get();
               if (stock.get().getQuantity() >= quantity){
                       OrderEntity orderEntity = new OrderEntity();
                       orderEntity.setStatus("Received");
                       orderEntity.setQuantity(quantity);
                       orderEntity.setStockEntity(stockEntity.getSkuEntity().getStockEntity());
                       orderR.save(orderEntity);
                       stock.get().setQuantity(stock.get().getQuantity()-quantity);
                        stockR.save(stock.get());
                        Optional <CartEntity> cartEntity = cartR.findById(stock.get().getSkuCode());
                    if(cartEntity.isPresent()) {
                        cartR.deleteById(cartEntity.get().getSkuEntity().getSkuCode());
                    }

                       return "Order Placed Successfully";
           }

               return "Out Of Stock";
           }
           return "Not Available";
       }
       public OrderModel getOrderStatus(Integer orderCode) {
           Optional<OrderEntity> orderEntity = orderR.findById(orderCode);
           Double i = orderEntity.get().getQuantity() * orderEntity.get().getStockEntity().getSkuEntity().getPriceEntity().getPrice();

           if (orderEntity.isPresent()) {
               OrderModel orderModel = new OrderModel();
               orderModel.setOrderCode(orderEntity.get().getOrderCode());
               orderModel.setProductName(orderEntity.get().getStockEntity().getSkuEntity().getProductEntity().getProductName());
               orderModel.setSkuCode(orderEntity.get().getStockEntity().getSkuCode());
               orderModel.setSkuSize(orderEntity.get().getStockEntity().getSkuEntity().getSize());
               orderModel.setQuantity(orderEntity.get().getQuantity());
               orderModel.setPrice(orderEntity.get().getStockEntity().getSkuEntity().getPriceEntity().getPrice());
               orderModel.setStatus(orderEntity.get().getStatus());
               orderModel.setTotalprice(i);
               return orderModel;
           }
           return null;
       }
       public String processing(Integer orderCode)
       {
           Optional<OrderEntity> orderEntity=orderR.findById(orderCode);
           if (orderEntity.isPresent()) {
               if (orderEntity.get().getStatus().equals("Received")) {
                   orderEntity.get().setStatus("Processing");
                   orderR.save(orderEntity.get());
                   ProductionEntity productionEntity = new ProductionEntity();
                   productionEntity.setOrderCode(orderEntity.get().getOrderCode());
                   productionEntity.setStatus(orderEntity.get().getStatus());
                   productionR.save(productionEntity);
                   OrderModel orderModel = new OrderModel();
                   orderModel.setOrderCode(orderEntity.get().getOrderCode());
                   orderModel.setProductName(orderEntity.get().getStockEntity().getSkuEntity().getProductEntity().getProductName());
                   orderModel.setSkuCode(orderEntity.get().getStockEntity().getSkuCode());
                   orderModel.setSkuSize(orderEntity.get().getStockEntity().getSkuEntity().getSize());
                   orderModel.setQuantity(orderEntity.get().getQuantity());
                   orderModel.setPrice(orderEntity.get().getStockEntity().getSkuEntity().getPriceEntity().getPrice());
                   orderModel.setStatus(orderEntity.get().getStatus());
                   return "***************Processing****************";
               }
               return "Order Not Received";
           }
           return "Product with "+orderEntity.get().getOrderCode()+"is not exist";
       }
       public  String packing(Integer orderCode)
       {
           Optional<OrderEntity> orderEntity=orderR.findById(orderCode);
           if (orderEntity.isPresent()) {
               if (orderEntity.get().getStatus().equals("Processing")) {
                   orderEntity.get().setStatus("Packing");
                   orderR.save(orderEntity.get());
                   PackingEntity packingEntity = new PackingEntity();
                   packingEntity.setOrderCode(orderEntity.get().getOrderCode());
                   packingEntity.setStatus(orderEntity.get().getStatus());
                   packingR.save(packingEntity);
                   OrderModel orderModel = new OrderModel();
                   orderModel.setOrderCode(orderEntity.get().getOrderCode());
                   orderModel.setProductName(orderEntity.get().getStockEntity().getSkuEntity().getProductEntity().getProductName());
                   orderModel.setSkuCode(orderEntity.get().getStockEntity().getSkuCode());
                   orderModel.setSkuSize(orderEntity.get().getStockEntity().getSkuEntity().getSize());
                   orderModel.setQuantity(orderEntity.get().getQuantity());
                   orderModel.setPrice(orderEntity.get().getStockEntity().getSkuEntity().getPriceEntity().getPrice());
                   orderModel.setStatus(orderEntity.get().getStatus());
                   return "**************Packing**************";
               }
               return "Order Not Processed";
           }
           return "Product with "+orderEntity.get().getOrderCode()+"is not exist";
       }
       public String shipping(Integer orderCode)
       {
           Optional<OrderEntity> orderEntity=orderR.findById(orderCode);
           if (orderEntity.isPresent()) {
               if (orderEntity.get().getStatus().equals("Packing")) {
                   orderEntity.get().setStatus("Shipping");
                   orderR.save(orderEntity.get());
                   ShippingEntity shippingEntity = new ShippingEntity();
                   shippingEntity.setOrderCode(orderEntity.get().getOrderCode());
                   shippingEntity.setStatus(orderEntity.get().getStatus());
                   shippingR.save(shippingEntity);
                   OrderModel orderModel = new OrderModel();
                   orderModel.setOrderCode(orderEntity.get().getOrderCode());
                   orderModel.setProductName(orderEntity.get().getStockEntity().getSkuEntity().getProductEntity().getProductName());
                   orderModel.setSkuCode(orderEntity.get().getStockEntity().getSkuCode());
                   orderModel.setSkuSize(orderEntity.get().getStockEntity().getSkuEntity().getSize());
                   orderModel.setQuantity(orderEntity.get().getQuantity());
                   orderModel.setPrice(orderEntity.get().getStockEntity().getSkuEntity().getPriceEntity().getPrice());
                   orderModel.setStatus(orderEntity.get().getStatus());
                   return "**************Shipping**************";
               }
               return "Order Not Packed";

           }
           return "Product with "+orderEntity.get().getOrderCode()+"is not exist";
       }
       public String delivered(Integer orderCode)
       {
           Optional<OrderEntity> orderEntity=orderR.findById(orderCode);
           if (orderEntity.isPresent()) {
               if (orderEntity.get().getStatus().equals("Shipping")) {
                   orderEntity.get().setStatus("Delivered");
                   orderR.save(orderEntity.get());
                   DeliveredEntity deliveredEntity = new DeliveredEntity();
                   deliveredEntity.setOrderCode(orderEntity.get().getOrderCode());
                   deliveredEntity.setStatus(orderEntity.get().getStatus());
                   deliveredR.save(deliveredEntity);
                   OrderModel orderModel = new OrderModel();
                   orderModel.setOrderCode(orderEntity.get().getOrderCode());
                   orderModel.setProductName(orderEntity.get().getStockEntity().getSkuEntity().getProductEntity().getProductName());
                   orderModel.setSkuCode(orderEntity.get().getStockEntity().getSkuCode());
                   orderModel.setSkuSize(orderEntity.get().getStockEntity().getSkuEntity().getSize());
                   orderModel.setQuantity(orderEntity.get().getQuantity());
                   orderModel.setPrice(orderEntity.get().getStockEntity().getSkuEntity().getPriceEntity().getPrice());
                   orderModel.setStatus(orderEntity.get().getStatus());
                   return "**************Delivered**************";
               }
               return "Order Not Shipped";

           }
           return "Product with "+orderEntity.get().getOrderCode()+"is not exist";

       }
       public String returned(Integer orderCode)
       {
           Optional<OrderEntity> orderEntity=orderR.findById(orderCode);
           if (orderEntity.isPresent()) {
               if (orderEntity.get().getStatus().equals("Delivered")) {
                   orderEntity.get().setStatus("Returned");
                   orderR.save(orderEntity.get());
                   ReturnedEntity returnedEntity = new ReturnedEntity();
                   returnedEntity.setOrderCode(orderEntity.get().getOrderCode());
                   returnedEntity.setStatus(orderEntity.get().getStatus());
                   returnedEntity.setQuantity(orderEntity.get().getQuantity());
                   returnedR.save(returnedEntity);
                   StockEntity stockEntity=stockR.getById(orderEntity.get().getStockEntity().getSkuCode());
                   stockEntity.setQuantity(stockEntity.getQuantity()+orderEntity.get().getQuantity());
                   stockR.save(stockEntity);
                   OrderModel orderModel = new OrderModel();
                   orderModel.setOrderCode(orderEntity.get().getOrderCode());
                   orderModel.setProductName(orderEntity.get().getStockEntity().getSkuEntity().getProductEntity().getProductName());
                   orderModel.setSkuCode(orderEntity.get().getStockEntity().getSkuCode());
                   orderModel.setSkuSize(orderEntity.get().getStockEntity().getSkuEntity().getSize());
                   orderModel.setQuantity(orderEntity.get().getQuantity());
                   orderModel.setPrice(orderEntity.get().getStockEntity().getSkuEntity().getPriceEntity().getPrice());
                   orderModel.setStatus(orderEntity.get().getStatus());
                   return "**************Returned**************";
               }
               return "Order Not Delivered";

           }
           return "Product with "+orderEntity.get().getOrderCode()+"is not exist";


       }
       public String update(Integer skuCode,Integer quantity)
       {
           Optional<StockEntity> stock=stockR.findById(skuCode);
           if(stock.isPresent())
           {
               stock.get().setQuantity(quantity+stock.get().getQuantity());
               stockR.save(stock.get());
               return "Quantity Updated Successfully";
           }
           else
               return "There is no such skuCode";
       }

}
