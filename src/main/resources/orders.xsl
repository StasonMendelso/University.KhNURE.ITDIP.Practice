<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:tns="http://stanislav.hlova/shoe-shop/orders"
                version="1.0">
    <xsl:variable name="selectedStatus" select="'На доставці'"/>

    <xsl:template match="/">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Shoe shop</title>
                <style>
                    *, *:after, *:before {
                    box-sizing: border-box;
                    padding: 0;
                    margin: 0;
                    transition: .2s ease-in-out;
                    }

                    body {
                    font-family: 'Open Sans', Arial, sans-serif;
                    font-size: 14px;
                    line-height: 1.5;
                    color: #373737;
                    background: #f7f7f7;
                    padding: 20px;
                    }

                    .wrapper {
                    width: 100%;
                    margin: auto;
                    padding: 20px;
                    background: #fff;
                    border-radius: 8px;
                    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
                    }

                    h2 {
                    margin-bottom: 20px;
                    color: #2c3e50;
                    }

                    table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-bottom: 20px;
                    }

                    th, td {
                    padding: 10px;
                    text-align: left;
                    border-bottom: 1px solid #ddd;
                    }

                    th {
                    background-color: #f2f2f2;
                    color: #333;
                    }

                    tr:hover {
                    background-color: #f9f9f9;
                    }
                    .order-info__row{
                    display: none;
                    }
                    .order-info__row.active{
                    display: table-row;
                    }
                    .order-info__container {
                    display: none;
                    margin-top: 10px;
                    padding: 15px;
                    background-color: #f9f9f9;
                    border: 1px solid #ddd;
                    border-radius: 5px;
                    }

                    .order-info__container.active {
                    display: block;
                    }

                    .contacts__container {
                    display: grid;
                    grid-template-columns: repeat(2, 1fr);
                    gap: 20px;
                    margin-top: 15px;
                    }

                    .toggle__button {
                    background-color: #3498db;
                    color: white;
                    border: none;
                    padding: 10px 15px;
                    border-radius: 5px;
                    cursor: pointer;
                    transition: background-color 0.3s;
                    }

                    .toggle__button:hover {
                    background-color: #2980b9;
                    }

                    caption {
                    margin: 10px 0;
                    font-weight: bold;
                    text-align: left;
                    }

                    p span {
                    font-weight: bold;
                    }
                    .no-orders {
                    color: #e74c3c;
                    font-weight: bold;
                    font-size: 16px;
                    margin-top: 20px;
                    }
                </style>
            </head>
            <body>
                <div class="wrapper">
                    <h2>Замовлення (Статус: <xsl:value-of select="$selectedStatus"/>)
                    </h2>
                    <table>
                        <thead>
                            <tr>
                                <th>Ідентифікатор замовлення</th>
                                <th>Покупець</th>
                                <th>Отримувач</th>
                                <th>Кількість</th>
                                <th>Знижка</th>
                                <th>Всього</th>
                                <th>Статус</th>
                                <th>Дії</th>
                            </tr>
                        </thead>
                        <tbody>
                            <xsl:choose>
                                <xsl:when test="tns:orders/tns:order[tns:status = $selectedStatus]">
                                    <xsl:for-each select="tns:orders/tns:order[tns:status = $selectedStatus]">
                                        <tr class="order-row">
                                            <td>
                                                <xsl:value-of select="@id"/>
                                            </td>
                                            <td>
                                                <xsl:value-of select="tns:buyer/tns:lastName"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of select="tns:buyer/tns:firstName"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of select="tns:buyer/tns:middleName"/>
                                                <xsl:text>, </xsl:text>
                                                <xsl:value-of select="tns:buyer/tns:telephoneNumber"/>
                                            </td>
                                            <td>
                                                <xsl:value-of select="tns:receiver/tns:lastName"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of select="tns:receiver/tns:firstName"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of select="tns:receiver/tns:middleName"/>
                                                <xsl:text>, </xsl:text>
                                                <xsl:value-of select="tns:receiver/tns:telephoneNumber"/>
                                            </td>
                                            <td>
                                                <xsl:value-of select="sum(tns:orderItems/tns:orderItem/tns:count)"/>
                                            </td>
                                            <td>
                                                <xsl:value-of select="sum(tns:orderItems/tns:orderItem/tns:discount)"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of
                                                        select="tns:orderItems/tns:orderItem/tns:discount/@currency"/>
                                            </td>
                                            <td>
                                                <xsl:value-of select="sum(tns:orderItems/tns:orderItem/tns:total)"/>
                                                <xsl:text> </xsl:text>
                                                <xsl:value-of
                                                        select="tns:orderItems/tns:orderItem/tns:total/@currency"/>
                                            </td>
                                            <td>
                                                <xsl:value-of select="tns:status"/>
                                            </td>
                                            <td>
                                                <button class="toggle__button" data-order-id="{@id}">Open/Close</button>
                                            </td>
                                        </tr>
                                        <tr class="order-info__row">
                                            <td colspan="8">
                                                <div class="order-info__container" data-order-id="{@id}">
                                                    <xsl:if test="tns:delivery">
                                                        <div>
                                                            <h3>Доставка</h3>
                                                            <p>
                                                                <span>Сервіс з доставки:</span>
                                                                <xsl:value-of
                                                                        select="tns:delivery/tns:delivery-service"/>
                                                            </p>
                                                            <p>
                                                                <span>Адреса:</span>
                                                                <xsl:value-of select="tns:delivery/tns:address"/>
                                                            </p>
                                                            <p>
                                                                <span>Відділення:</span>
                                                                <xsl:value-of
                                                                        select="tns:delivery/tns:departmentNumber"/>
                                                            </p>
                                                            <p>
                                                                <span>Статус:</span>
                                                                <xsl:value-of select="tns:delivery/tns:status"/>
                                                            </p>
                                                        </div>
                                                    </xsl:if>

                                                    <div class="contacts__container">
                                                        <div>
                                                            <h3>Покупець</h3>
                                                            <div>
                                                                <p>
                                                                    <span>Email:</span>
                                                                    <xsl:value-of select="tns:buyer/tns:email"/>
                                                                </p>
                                                                <p>
                                                                    <span>Прізвище:</span>
                                                                    <xsl:value-of select="tns:buyer/tns:lastName"/>
                                                                </p>
                                                                <p>
                                                                    <span>Ім'я:</span>
                                                                    <xsl:value-of select="tns:buyer/tns:firstName"/>
                                                                </p>
                                                                <p>
                                                                    <span>Побатькові:</span>
                                                                    <xsl:value-of select="tns:buyer/tns:middleName"/>
                                                                </p>
                                                                <p>
                                                                    <span>Номер телефону:</span>
                                                                    <xsl:value-of
                                                                            select="tns:buyer/tns:telephoneNumber"/>
                                                                </p>
                                                            </div>
                                                        </div>
                                                        <div>
                                                            <h3>Отримувач</h3>
                                                            <div>
                                                                <p>
                                                                    <span>Email:</span>
                                                                    <xsl:value-of select="tns:receiver/tns:email"/>
                                                                </p>
                                                                <p>
                                                                    <span>Прізвище:</span>
                                                                    <xsl:value-of select="tns:receiver/tns:lastName"/>
                                                                </p>
                                                                <p>
                                                                    <span>Ім'я:</span>
                                                                    <xsl:value-of select="tns:receiver/tns:firstName"/>
                                                                </p>
                                                                <p>
                                                                    <span>Побатькові:</span>
                                                                    <xsl:value-of select="tns:receiver/tns:middleName"/>
                                                                </p>
                                                                <p>
                                                                    <span>Номер телефону:</span>
                                                                    <xsl:value-of
                                                                            select="tns:receiver/tns:telephoneNumber"/>
                                                                </p>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <table style="width: 100%">
                                                        <caption
                                                                style="font-size: 20px; text-align: left; margin-bottom: 10px;">
                                                            Товари у замовленні
                                                        </caption>
                                                        <thead>
                                                            <tr>
                                                                <th>Найменування</th>
                                                                <th>Кількість</th>
                                                                <th>Ціна</th>
                                                                <th>Сума</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <xsl:for-each select="tns:orderItems/tns:orderItem">
                                                                <tr>
                                                                    <td>
                                                                        <xsl:value-of select="tns:shoe/tns:name"/>
                                                                    </td>
                                                                    <td>
                                                                        <xsl:value-of select="tns:count"/>
                                                                    </td>
                                                                    <td>
                                                                        <xsl:value-of select="tns:price"/>
                                                                        <xsl:text> </xsl:text>
                                                                        <xsl:value-of select="tns:price/@currency"/>
                                                                    </td>
                                                                    <td>
                                                                        <xsl:value-of select="tns:total"/>
                                                                        <xsl:text> </xsl:text>
                                                                        <xsl:value-of select="tns:total/@currency"/>
                                                                    </td>
                                                                </tr>
                                                            </xsl:for-each>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </td>
                                        </tr>
                                    </xsl:for-each>
                                </xsl:when>

                                <xsl:otherwise>
                                    <div class="no-orders">Замовлень не знайдено</div>
                                </xsl:otherwise>
                            </xsl:choose>
                        </tbody>
                    </table>
                </div>

                <script>
                    document.querySelectorAll(".toggle__button").forEach(btn => {
                    btn.addEventListener("click", () => {
                    let orderId = btn.getAttribute("data-order-id");
                    let orderDiv = document.querySelector(`div[data-order-id='${orderId}']`);
                    orderDiv.classList.toggle("active");
                    orderDiv.parentElement.parentElement.classList.toggle("active");
                    });
                    });
                </script>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
