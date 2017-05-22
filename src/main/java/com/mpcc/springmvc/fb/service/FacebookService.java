/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpcc.springmvc.fb.service;

import com.mpcc.springmvc.fb.model.CommentModel;
import com.mpcc.springmvc.fb.model.FBRealtimeData;
import com.mpcc.springmvc.fb.model.PostModel;
import java.util.List;

/**
 *
 * @author hieuhd
 */
public interface FacebookService {

    List<PostModel> getAllPost();

    boolean insertPost(FBRealtimeData data);

    boolean updatePost(FBRealtimeData data);

    boolean deletePost(FBRealtimeData data);

    List<CommentModel> getAllComment();

    List<CommentModel> getAllCommentByPost(String postId);

    boolean insertComment(FBRealtimeData data);

    boolean updateComment(FBRealtimeData data);

    boolean deleteComment(FBRealtimeData data);

}
