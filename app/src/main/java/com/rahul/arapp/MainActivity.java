package com.rahul.arapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {
private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arFragment= (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ar_frag);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            Anchor anchor=hitResult.createAnchor();
            ModelRenderable.builder()
                    .setSource(this, Uri.parse("" +
                            ".sfb"))
                    .build()
                    .thenAccept(modelRenderable -> createScene(modelRenderable,anchor))
                    .exceptionally(throwable -> {
                        Toast.makeText(this, "EXCEPTION", Toast.LENGTH_SHORT).show();

                        return  null;
                    });
        });
    }

    private void createScene(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode node=new AnchorNode(anchor);
        TransformableNode transformableNode=new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(node);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(node);
        transformableNode.select();
    }
}
