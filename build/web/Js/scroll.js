/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function scrollFunction() {
    var header = document.querySelector(".headerr");
    var logo = document.getElementById("logo");
    var cart = document.getElementById("cart");
    
    if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
        header.classList.add("scrolled");
        logo.src = "Picture/Logo1.png"; // Đường dẫn tới logo màu đen
        cart.src = "Picture/shopping-cart.png"; 
    } else {
        header.classList.remove("scrolled");
        logo.src = "Picture/Logo1.2.png"; // Đường dẫn tới logo màu trắng
        cart.src = "Picture/shopping-cart1.1.png";
    }
}

window.onscroll = function () {
    scrollFunction();
};






