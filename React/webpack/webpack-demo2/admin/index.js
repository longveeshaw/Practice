import "style-loader!css-loader!./admin.css";
import "style-loader!css-loader!sass-loader!./content.scss";
import 'jquery'

document.getElementById('content').innerText=`
    hello admin
    welcome!
    `