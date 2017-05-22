/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.fb.service;

import com.mpcc.springmvc.fb.model.CommentModel;
import com.mpcc.springmvc.fb.model.FBRealtimeData;
import com.mpcc.springmvc.fb.model.PostModel;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hieuhd
 */
@Service("FacebookService")
@Transactional
public class FacebookServiceImpl implements FacebookService {

    @Autowired(required = true)
    HttpServletRequest servletRequest;
    private Connection connection;

    public FacebookServiceImpl() {

    }

    public List<PostModel> getAllPost() {
        DBUtility dBUtility = new DBUtility();
        try {
            connection = dBUtility.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = " SELECT id, content, sender_id, sender_name, created_time, time FROM fb_post ";

        Statement stmt;

        ArrayList<PostModel> list = new ArrayList<PostModel>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            PostModel post;
            while (rs != null && rs.next()) {
                post = new PostModel();
                post.setPost_id(rs.getString("id"));
                post.setMessage(rs.getString("content"));
                post.setSender_id(rs.getString("sender_id"));
                post.setSender_name(rs.getString("sender_name"));
                post.setCreated_time(rs.getString("created_time"));
                post.setTime(rs.getString("time"));
                list.add(post);
            }
            if (rs != null) {
                rs.close();
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;

    }

    public boolean insertPost(FBRealtimeData data) {
        DBUtility dBUtility = new DBUtility();
        try {
            connection = dBUtility.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean kq = false;
        String sql = "INSERT INTO fb_post(id, content, sender_id, sender_name, created_time, time) VALUES(?,?,?,?,?,?) ";

        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, data.getEntry()[0].getChanges()[0].getValue().getPost_id());
            pstmt.setString(2, data.getEntry()[0].getChanges()[0].getValue().getMessage());
            pstmt.setString(3, data.getEntry()[0].getChanges()[0].getValue().getSender_id());
            pstmt.setString(4, data.getEntry()[0].getChanges()[0].getValue().getSender_name());
            pstmt.setString(5, data.getEntry()[0].getChanges()[0].getValue().getCreated_time());
            pstmt.setString(6, data.getEntry()[0].getTime());

            if (pstmt.executeUpdate() > 0) {
                kq = true;
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    public boolean updatePost(FBRealtimeData data) {
        DBUtility dBUtility = new DBUtility();
        try {
            connection = dBUtility.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean kq = false;
        String sql = "UPDATE fb_post SET  content = ?, sender_name = ?, time = ? WHERE id = ?";

        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, data.getEntry()[0].getChanges()[0].getValue().getMessage());
            pstmt.setString(2, data.getEntry()[0].getChanges()[0].getValue().getSender_name());
            pstmt.setString(3, data.getEntry()[0].getTime());
            pstmt.setString(4, data.getEntry()[0].getChanges()[0].getValue().getPost_id());

            if (pstmt.executeUpdate() > 0) {
                kq = true;
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    public boolean deletePost(FBRealtimeData data) {
        boolean kq = false;
        try {
            DBUtility dBUtility = new DBUtility();
            connection = dBUtility.getConnection();
            String sql = " DELETE FROM fb_post WHERE id = ? ";
            PreparedStatement pstmt;
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, data.getEntry()[0].getChanges()[0].getValue().getPost_id());
                if (pstmt.executeUpdate() > 0) {
                    kq = true;
                }
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kq;

    }

    public List<CommentModel> getAllComment() {
        DBUtility dBUtility = new DBUtility();
        try {
            connection = dBUtility.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = " SELECT c.id, c.content, c.post_id, c.parent_id, c.sender_id, c.sender_name, c.created_time, c.time, u.picture FROM fb_comment c JOIN fb_user u ON c.sender_id = u.id ";

        Statement stmt;

        ArrayList<CommentModel> list = new ArrayList<CommentModel>();

        try {
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            CommentModel comment;
            while (rs != null && rs.next()) {
                comment = new CommentModel();
                comment.setComment_id(rs.getString("c.id"));
                comment.setMessage(rs.getString("c.content"));
                comment.setPost_id(rs.getString("c.post_id"));
                comment.setParent_id(rs.getString("c.parent_id"));
                comment.setSender_id(rs.getString("c.sender_id"));
                comment.setSender_name(rs.getString("c.sender_name"));
                comment.setSender_picture(rs.getString("u.picture"));
                comment.setCreated_time(rs.getString("c.created_time"));
                comment.setTime(rs.getString("c.time"));
                list.add(comment);
            }
            if (rs != null) {
                rs.close();
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;

    }

    public List<CommentModel> getAllCommentByPost(String postId) {
        DBUtility dBUtility = new DBUtility();
        try {
            connection = dBUtility.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = " SELECT id, content, post_id, parent_id, sender_id, sender_name, created_time, time FROM fb_comment WHERE post_id = ?";

        PreparedStatement pstmt;

        ArrayList<CommentModel> list = new ArrayList<CommentModel>();

        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, postId);
            ResultSet rs = pstmt.executeQuery();
            CommentModel comment;
            while (rs != null && rs.next()) {
                comment = new CommentModel();
                comment.setComment_id(rs.getString("id"));
                comment.setMessage(rs.getString("content"));
                comment.setPost_id(rs.getString("post_id"));
                comment.setParent_id(rs.getString("parent_id"));
                comment.setSender_id(rs.getString("sender_id"));
                comment.setSender_name(rs.getString("sender_name"));
                comment.setCreated_time(rs.getString("created_time"));
                comment.setTime(rs.getString("time"));
                list.add(comment);
            }
            if (rs != null) {
                rs.close();
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;

    }

    public boolean insertComment(FBRealtimeData data) {
        DBUtility dBUtility = new DBUtility();
        try {
            connection = dBUtility.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean kq = false;
        String sql = "INSERT INTO fb_comment(id, content, post_id, parent_id, sender_id, sender_name, created_time, time) VALUES(?,?,?,?,?,?,?,?) ";

        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, data.getEntry()[0].getChanges()[0].getValue().getComment_id());
            pstmt.setString(2, data.getEntry()[0].getChanges()[0].getValue().getMessage());
            pstmt.setString(3, data.getEntry()[0].getChanges()[0].getValue().getPost_id());
            pstmt.setString(4, data.getEntry()[0].getChanges()[0].getValue().getParent_id());
            pstmt.setString(5, data.getEntry()[0].getChanges()[0].getValue().getSender_id());
            pstmt.setString(6, data.getEntry()[0].getChanges()[0].getValue().getSender_name());
            pstmt.setString(7, data.getEntry()[0].getChanges()[0].getValue().getCreated_time());
            pstmt.setString(8, data.getEntry()[0].getTime());

            if (pstmt.executeUpdate() > 0) {
                kq = true;
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    public boolean updateComment(FBRealtimeData data) {
        DBUtility dBUtility = new DBUtility();
        try {
            connection = dBUtility.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean kq = false;
        String sql = "UPDATE fb_comment SET  content = ?, sender_name = ?, time = ? WHERE id = ?";

        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, data.getEntry()[0].getChanges()[0].getValue().getMessage());
            pstmt.setString(2, data.getEntry()[0].getChanges()[0].getValue().getSender_name());
            pstmt.setString(3, data.getEntry()[0].getTime());
            pstmt.setString(4, data.getEntry()[0].getChanges()[0].getValue().getComment_id());

            if (pstmt.executeUpdate() > 0) {
                kq = true;
            }
            pstmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kq;
    }

    public boolean deleteComment(FBRealtimeData data) {
        boolean kq = false;
        try {
            DBUtility dBUtility = new DBUtility();
            connection = dBUtility.getConnection();
            String sql = " DELETE FROM fb_comment WHERE id = ? ";
            PreparedStatement pstmt;
            try {
                pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, data.getEntry()[0].getChanges()[0].getValue().getComment_id());
                if (pstmt.executeUpdate() > 0) {
                    kq = true;
                }
                pstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FacebookServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kq;

    }
}
