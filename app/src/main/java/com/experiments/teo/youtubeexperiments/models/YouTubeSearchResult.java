package com.experiments.teo.youtubeexperiments.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by teo on 4/8/15.
 */
public class YouTubeSearchResult {

    @Expose
    private String kind;

    @Expose
    private ArrayList<ItemSearchResult> items;

    @Expose
    private String nextPageToken;

    @Expose
    private HashMap<String, Integer> pageInfo;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public HashMap getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(HashMap pageInfo) {
        this.pageInfo = pageInfo;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ArrayList<ItemSearchResult> getItem() {
        return items;
    }

    public void setItem(ArrayList<ItemSearchResult> item) {
        this.items = item;
    }

    public class ItemSearchResult {

        @Expose
        private String kind;

        @Expose
        private String etag;
        @Expose
        private HashMap<String, String> id;
        @Expose
        private Snippet snippet;

        public HashMap<String, String> getId() {
            return id;
        }

        public void setId(HashMap<String, String> id) {
            this.id = id;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getEtag() {
            return etag;
        }

        public void setEtag(String etag) {
            this.etag = etag;
        }

        public Snippet getSnippet() {
            return snippet;
        }

        public void setSnippet(Snippet snippet) {
            this.snippet = snippet;
        }

        public class Snippet {
            @Expose
            private String title;
            @Expose
            private String description;
            @Expose
            private String channelTitle;
            @Expose
            private PreviewImages thumbnails;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getChannelTitle() {
                return channelTitle;
            }

            public void setChannelTitle(String channelTitle) {
                this.channelTitle = channelTitle;
            }

            public PreviewImages getThumbnails() {
                return thumbnails;
            }

            public void setThumbnails(PreviewImages thumbnails) {
                this.thumbnails = thumbnails;
            }

            public class PreviewImages {
                @Expose
                private HashMap<String, String> medium;
                private HashMap<String, String> high;

                public HashMap<String, String> getMedium() {
                    return medium;
                }

                public void setMedium(HashMap<String, String> medium) {
                    this.medium = medium;
                }

                public HashMap<String, String> getHigh() {
                    return high;
                }

                public void setHigh(HashMap<String, String> high) {
                    this.high = high;
                }
            }
        }

    }
}

/*
"publishedAt": "2010-06-17T08:20:57.000Z",
        "channelId": "UCqaRNug8RUB4ABnAlp6YZdQ",
        "title": "Litfiba - Fata Morgana",
        "description": "Oh, vedo tutto attraverso sabbia rossa e deserto Ho sete, ho sete di te che non sei qui Stella caduta dagli occhi, Che voli sul mio deserto Ho sete, le nuvole mi ...",
        "thumbnails": {
        "default": {
        "url": "https://i.ytimg.com/vi/r8jthXY5xT8/default.jpg"
        },
        "medium": {
        "url": "https://i.ytimg.com/vi/r8jthXY5xT8/mqdefault.jpg"
        },
        "high": {
        "url": "https://i.ytimg.com/vi/r8jthXY5xT8/hqdefault.jpg"
        }
        },
        "channelTitle": "CLBubu",
        "liveBroadcastContent": "none"
        }
*/
