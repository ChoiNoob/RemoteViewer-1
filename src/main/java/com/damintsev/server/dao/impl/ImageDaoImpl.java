package com.damintsev.server.dao.impl;

import com.damintsev.common.uientity.Image;
import com.damintsev.server.dao.ImageDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo написать комментарии
 */
@Repository
public class ImageDaoImpl implements ImageDao {

    private static Logger logger = Logger.getLogger(ImageDaoImpl.class);

    @Autowired
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void saveImage(Image image) {
        em.merge(image);
    }

    @Override
    public Image getImage(Long id) {
//        Image image = null;
//        String query ="SELECT * FROM images WHERE id = ?";
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setLong(1, id);
//            ResultSet resultSet = statement.executeQuery();
//            if(resultSet.next()) {
//                image = new Image();
//                image.setId(id);
////                image.setType(resultSet.getString("TYPE"));
//                image.setHeight(resultSet.getInt("height"));
//                image.setWidth(resultSet.getInt("width"));
//                Blob imageBlob = resultSet.getBlob("data");
//                image.setContent(imageBlob.getBytes(1, (int) imageBlob.length()));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(); //todo change this !!!
//        }
        Query query = em.createQuery("SELECT i FROM Image i WHERE i.id = :id");
        query.setParameter("id", id);
        return (Image) query.getSingleResult();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
