package org.alex_group.servlet;

import org.alex_group.dto.CarDTO;
import org.alex_group.mapper.AppMapper;
import org.alex_group.repository.CarRepository;
import org.alex_group.repository.impl.CarRepositoryImpl;
import org.alex_group.service.CarService;
import org.alex_group.service.impl.CarServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/cars/create")
public class CarCreateServlet extends HttpServlet {
    private final CarService carService;
    private final AppMapper appMapper;

    public CarCreateServlet() {
        CarRepository carRepository = new CarRepositoryImpl();
        this.carService = new CarServiceImpl(carRepository);
        this.appMapper = AppMapper.INSTANCE;

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarDTO carDTO = new CarDTO(
                req.getParameter("markName"),
                req.getParameter("modelName"),
                Integer.parseInt(req.getParameter("productionYear")),
                Integer.parseInt(req.getParameter("price")),
                req.getParameter("productionCountry"),
                req.getParameter("colour"),
                Integer.parseInt(req.getParameter("count"))
        );

        try {
            carService.createCar(appMapper.toCar(carDTO));
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
           resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
