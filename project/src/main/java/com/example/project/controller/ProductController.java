package com.example.project.controller;

import com.example.project.entity.CartEntity;
import com.example.project.model.CartModel;
import com.example.project.model.OrderModel;
import com.example.project.model.ProductModel;
import com.example.project.model.SkuModel;
import com.example.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @RequestMapping(value="/addProduct",method = RequestMethod.POST)
    private String addProduct(@RequestBody ProductModel productModel)
    {
       return productService.addProduct(productModel);
    }
    @RequestMapping(value="/addSku/{productCode}",method = RequestMethod.POST)
    private String addSku( @PathVariable Integer productCode,@RequestBody SkuModel skuModel)
    {
      return   productService.addSku(skuModel,productCode);
    }
    @RequestMapping(value="/addStock/{skuCode}/{quantity}",method = RequestMethod.POST)
    private String addStock( @PathVariable Integer skuCode,@PathVariable Integer quantity)
    {
        return productService.addStock(skuCode,quantity);
    }
    @RequestMapping(value="/addCart/{skuCode}/{quantity}",method = RequestMethod.POST)
    private String addCart( @PathVariable Integer skuCode,@PathVariable Integer quantity)
    {
        return productService.addCart(skuCode,quantity);
    }
    @RequestMapping(value="/viewCart",method = RequestMethod.GET)
    private List<CartModel> viewCart()
    {
        return productService.viewCart();
    }
    @RequestMapping(value="/placeOrder/{skuCode}/{quantity}",method = RequestMethod.POST)
    private String placeOrder(@PathVariable Integer skuCode,@PathVariable Integer quantity)
    {
        return productService.placeOrder(skuCode,quantity);
    }
    @RequestMapping(value="/getOrderStatus/{orderCode}",method = RequestMethod.GET)
    private OrderModel getOrderStatus(@PathVariable Integer orderCode)
    {
        return productService.getOrderStatus(orderCode);
    }
    @RequestMapping(value="/processing/{orderCode}",method = RequestMethod.POST)
    private String processing(@PathVariable Integer orderCode)
    {
        return productService.processing(orderCode);
    }
    @RequestMapping(value="/packing/{orderCode}",method = RequestMethod.POST)
    private String packing(@PathVariable Integer orderCode)
    {
        return productService.packing(orderCode);
    }
    @RequestMapping(value="/shipping/{orderCode}",method = RequestMethod.POST)
    private String shipping(@PathVariable Integer orderCode)
    {
        return productService.shipping(orderCode);
    }
    @RequestMapping(value="/delivered/{orderCode}",method = RequestMethod.POST)
    private String delivered(@PathVariable Integer orderCode)
    {
        return productService.delivered(orderCode);
    }
    @RequestMapping(value="/returned/{orderCode}",method = RequestMethod.POST)
    private String returned(@PathVariable Integer orderCode)
    {
        return productService.returned(orderCode);
    }
    @RequestMapping(value="/update/{skuCode}/{quantity}",method = RequestMethod.PUT)
    private String update( @PathVariable Integer skuCode,@PathVariable Integer quantity)
    {
        return productService.update(skuCode,quantity);
    }

}
