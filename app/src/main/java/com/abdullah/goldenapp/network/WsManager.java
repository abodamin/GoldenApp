package com.abdullah.goldenapp.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by jazib Shehraz on 11/4/2017.
 */

public class WsManager {
    StringRequest request;
    JsonObjectRequest jsonObjectRequest;
    JsonArrayRequest jsonArrayRequest;
    private Context context;

    public WsManager(Context context) {
        this.context = context;
    }


    public void postAsJson(final JSONObject mMmap, final String token, String url, final WSResponse wsResponse) {
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, mMmap, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                wsResponse.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.fillInStackTrace();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    wsResponse.onError("Time out Error or No Connection");
                } else if (error instanceof AuthFailureError) {
                    wsResponse.onError("Auth Failure Error");
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null) {
                        try {
                            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            wsResponse.onSuccess(res);
                            Log.d("WSManager Class", response.headers.toString());
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                            Log.d("WSManager Class", res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                            Log.d("WSManager Class", e1.getMessage());
                            wsResponse.onError("Server Error");
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                            Log.d("WSManager Class", e2.getMessage());
                            wsResponse.onError("Server Error");
                        }
                    }
                } else if (error instanceof NetworkError) {
                    wsResponse.onError("Network Error");
                } else if (error instanceof ParseError) {
                    wsResponse.onError("Parser Error");
                } else {
                    wsResponse.onError(error.getLocalizedMessage());
                }
            }
        }) {
            //            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", token);

                return headers;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public void postAsArrayReq(final JSONArray mMmap, final String token, String url, final WSResponse wsResponse) {
        Log.d("res_ppp", mMmap.toString());
        jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, mMmap, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("res_ppp_responce", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("res_ppp_error", error.toString());
                error.fillInStackTrace();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    wsResponse.onError("Time out Error or No Connection");
                } else if (error instanceof AuthFailureError) {
                    wsResponse.onError("Auth Failure Error");
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null) {
                        try {
                            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            wsResponse.onSuccess(res);
                            Log.d("WSManager Class", response.headers.toString());
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                            Log.d("WSManager Class", res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                            Log.d("WSManager Class", e1.getMessage());
                            wsResponse.onError("Server Error");
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                            Log.d("WSManager Class", e2.getMessage());
                            wsResponse.onError("Server Error");
                        }
                    }
                } else if (error instanceof NetworkError) {
                    wsResponse.onError("Network Error");
                } else if (error instanceof ParseError) {
                    wsResponse.onError(error.toString());
                } else {
                    wsResponse.onError(error.getLocalizedMessage());
                }

            }
        }) {
            //            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", token);

                return headers;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getAsJson(final JSONObject mMmap, final String token, String language, String url, final WSResponse wsResponse) {
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, mMmap, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                wsResponse.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.fillInStackTrace();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    wsResponse.onError("Time out Error or No Connection");
                } else if (error instanceof AuthFailureError) {
                    wsResponse.onError("Auth Failure Error");
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null) {
                        try {
                            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            wsResponse.onSuccess(res);
                            Log.d("WSManager Class", response.headers.toString());
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                            Log.d("WSManager Class", res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                            Log.d("WSManager Class", e1.getMessage());
                            wsResponse.onError("Server Error");
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                            Log.d("WSManager Class", e2.getMessage());
                            wsResponse.onError("Server Error");
                        }
                    }
                } else if (error instanceof NetworkError) {
                    wsResponse.onError("Network Error");
                } else if (error instanceof ParseError) {
                    wsResponse.onError("Parser Error");
                } else {
                    wsResponse.onError(error.getLocalizedMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", token);

                return headers;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public void cancerlRequest() {
        if (request != null)
            request.cancel();
    }

    public void pupAsJson(final JSONObject mMmap, final String token, String language, String url, final WSResponse wsResponse) {
        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url, mMmap, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                wsResponse.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.fillInStackTrace();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    wsResponse.onError("Time out Error or No Connection");
                } else if (error instanceof AuthFailureError) {
                    wsResponse.onError("Auth Failure Error");
                } else if (error instanceof ServerError) {
                    NetworkResponse response = error.networkResponse;
                    if (response != null) {
                        try {
                            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            wsResponse.onSuccess(res);
                            Log.d("WSManager Class", response.headers.toString());
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                            Log.d("WSManager Class", res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                            Log.d("WSManager Class", e1.getMessage());
                            wsResponse.onError("Server Error");
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                            Log.d("WSManager Class", e2.getMessage());
                            wsResponse.onError("Server Error");
                        }
                    }
                } else if (error instanceof NetworkError) {
                    wsResponse.onError("Network Error");
                } else if (error instanceof ParseError) {
                    wsResponse.onError("Parser Error");
                } else {
                    wsResponse.onError(error.getLocalizedMessage());
                }
            }
        }) {
            //            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("x-access-token", token);

                return headers;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}


// As of f605da3 the following should work
//                NetworkResponse response = error.networkResponse;
//                if (error instanceof ServerError && response != null) {
//                    try {
//                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        Log.d("WSManager Class",response.headers.toString());
//                        // Now you can use any deserializer to make sense of data
//                        JSONObject obj = new JSONObject(res);
//                        Log.d("WSManager Class",res);
//                    } catch (UnsupportedEncodingException e1) {
//                        // Couldn't properly decode data to string
//                        e1.printStackTrace();
//                        Log.d("WSManager Class",e1.getMessage());
//                    } catch (JSONException e2) {
//                        // returned data is not JSONObject?
//                        e2.printStackTrace();
//                        Log.d("WSManager Class",e2.getMessage());
//                    }
//                }