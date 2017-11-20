package com.example.zuo81.meng.di.Component;

import com.example.zuo81.meng.di.scope.FragmentScope;
import com.example.zuo81.meng.ui.dictionary.DictionaryFragment;
import com.example.zuo81.meng.ui.gallery.GalleryFragment;
import com.example.zuo81.meng.ui.music.MusicMainFragment;
import com.example.zuo81.meng.ui.music.fragment.LocalMusicFragment;
import com.example.zuo81.meng.ui.music.fragment.SearchMusicFragment;

import dagger.Component;

/**
 * Created by zuo81 on 2017/11/17.
 */
@FragmentScope
@Component(dependencies = AppComponent.class)
public interface FragmentComponent {

    void inject(GalleryFragment fragment);

    void inject(DictionaryFragment fragment);

    void inject(LocalMusicFragment fragment);

    void inject(SearchMusicFragment fragment);

    void inject(MusicMainFragment fragment);
}
