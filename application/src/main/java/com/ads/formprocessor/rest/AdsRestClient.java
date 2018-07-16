package com.ads.formprocessor.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ListIterator;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.outbounddelivery.OutbDeliveryItem;


	
public class AdsRestClient {
		

		public String callService(String sXMLDataLabel) {

			//String sJSON1 = "{\"xdpTemplate\":\"PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCjw/eGZhIGdlbmVyYXRvcj0iQWRvYmVMaXZlQ3ljbGVEZXNpZ25lcl9WMTEuMC4xLjIwMTQwMjE4LjEuOTA3MTYyX1NBUCIgQVBJVmVyc2lvbj0iMy42LjEzMzI0LjAiPz4NCjx4ZHA6eGRwIHhtbG5zOnhkcD0iaHR0cDovL25zLmFkb2JlLmNvbS94ZHAvIiB0aW1lU3RhbXA9IjIwMTgtMDYtMThUMTQ6MjA6MDVaIiB1dWlkPSI2ZTkwNGMwYS01YjY4LTQxNTktYjExNC0yNDJjNGYzOTFmODQiPg0KPHRlbXBsYXRlIHhtbG5zOnhsaWZmPSJ1cm46b2FzaXM6bmFtZXM6dGM6eGxpZmY6ZG9jdW1lbnQ6MS4xIiB4bWxucz0iaHR0cDovL3d3dy54ZmEub3JnL3NjaGVtYS94ZmEtdGVtcGxhdGUvMy4zLyI+DQogICA8P2Zvcm1TZXJ2ZXIgZGVmYXVsdFBERlJlbmRlckZvcm1hdCBhY3JvYmF0MTAuMGR5bmFtaWM/Pg0KICAgPD9mb3JtU2VydmVyIGFsbG93UmVuZGVyQ2FjaGluZyAwPz4NCiAgIDw/Zm9ybVNlcnZlciBmb3JtTW9kZWwgYm90aD8+DQogICA8c3ViZm9ybSBuYW1lPSJmb3JtMSIgbGF5b3V0PSJ0YiIgbG9jYWxlPSJlbl9JTiIgcmVzdG9yZVN0YXRlPSJhdXRvIj4NCiAgICAgIDxwYWdlU2V0Pg0KICAgICAgICAgPHBhZ2VBcmVhIG5hbWU9IlBhZ2UxIiBpZD0iUGFnZTEiPg0KICAgICAgICAgICAgPGNvbnRlbnRBcmVhIHg9IjAuMjVpbiIgeT0iMC4yNWluIiB3PSI1NzZwdCIgaD0iNzU2cHQiLz4NCiAgICAgICAgICAgIDxtZWRpdW0gc3RvY2s9ImRlZmF1bHQiIHNob3J0PSI2MTJwdCIgbG9uZz0iNzkycHQiLz4NCiAgICAgICAgICAgIDw/dGVtcGxhdGVEZXNpZ25lciBleHBhbmQgMT8+PC9wYWdlQXJlYT4NCiAgICAgICAgIDw/dGVtcGxhdGVEZXNpZ25lciBleHBhbmQgMT8+PC9wYWdlU2V0Pg0KICAgICAgPHN1YmZvcm0gdz0iNTc2cHQiIGg9Ijc1NnB0Ij4NCiAgICAgICAgIDxmaWVsZCBuYW1lPSJUZXh0RmllbGQxIiB5PSI2LjM1bW0iIHg9IjYuMzVtbSIgdz0iNjJtbSIgaD0iOW1tIj4NCiAgICAgICAgICAgIDx1aT4NCiAgICAgICAgICAgICAgIDx0ZXh0RWRpdD4NCiAgICAgICAgICAgICAgICAgIDxib3JkZXI+DQogICAgICAgICAgICAgICAgICAgICA8ZWRnZSBzdHJva2U9Imxvd2VyZWQiLz4NCiAgICAgICAgICAgICAgICAgIDwvYm9yZGVyPg0KICAgICAgICAgICAgICAgICAgPG1hcmdpbi8+DQogICAgICAgICAgICAgICA8L3RleHRFZGl0Pg0KICAgICAgICAgICAgPC91aT4NCiAgICAgICAgICAgIDxmb250IHR5cGVmYWNlPSJBcmlhbCIvPg0KICAgICAgICAgICAgPG1hcmdpbiB0b3BJbnNldD0iMW1tIiBib3R0b21JbnNldD0iMW1tIiBsZWZ0SW5zZXQ9IjFtbSIgcmlnaHRJbnNldD0iMW1tIi8+DQogICAgICAgICAgICA8cGFyYSB2QWxpZ249Im1pZGRsZSIvPg0KICAgICAgICAgICAgPGNhcHRpb24gcmVzZXJ2ZT0iMjVtbSI+DQogICAgICAgICAgICAgICA8cGFyYSB2QWxpZ249Im1pZGRsZSIvPg0KICAgICAgICAgICAgICAgPHZhbHVlPg0KICAgICAgICAgICAgICAgICAgPHRleHQgeGxpZmY6cmlkPSIyNzNFNEY5RS05QTRDLTRBMDgtOEI3Ny1BMDk2MzA2NTUxNUEiPlRleHQgRmllbGQxPC90ZXh0Pg0KICAgICAgICAgICAgICAgPC92YWx1ZT4NCiAgICAgICAgICAgIDwvY2FwdGlvbj4NCiAgICAgICAgICAgIDxiaW5kIG1hdGNoPSJkYXRhUmVmIiByZWY9IiQuVGV4dEZpZWxkMSIvPg0KICAgICAgICAgPC9maWVsZD4NCiAgICAgICAgIDw/dGVtcGxhdGVEZXNpZ25lciBleHBhbmQgMT8+PC9zdWJmb3JtPg0KICAgICAgPHByb3RvLz4NCiAgICAgIDxkZXNjPg0KICAgICAgICAgPHRleHQgbmFtZT0idmVyc2lvbiI+MTEuMC4xLjIwMTQwMjE4LjEuOTA3MTYyLjkwMzgwMTwvdGV4dD4NCiAgICAgIDwvZGVzYz4NCiAgICAgIDw/dGVtcGxhdGVEZXNpZ25lciBleHBhbmQgMT8+DQogICAgICA8P3RlbXBsYXRlRGVzaWduZXIgSHlwaGVuYXRpb24gZXhjbHVkZUluaXRpYWxDYXA6MSwgZXhjbHVkZUFsbENhcHM6MSwgd29yZENoYXJDbnQ6NywgcmVtYWluQ2hhckNudDozLCBwdXNoQ2hhckNudDozPz4NCiAgICAgIDw/cmVuZGVyQ2FjaGUuc3Vic2V0ICJBcmlhbCIgMCAwIElTTy04ODU5LTEgNCA0MCAxMCAwMDAzMDAxNDAwMjkwMDM3MDA0NzAwNDgwMDRDMDA0RjAwNTcwMDVCIDFGVGRlaWx0eD8+PC9zdWJmb3JtPg0KICAgPD90ZW1wbGF0ZURlc2lnbmVyIERlZmF1bHRQcmV2aWV3RHluYW1pYyAxPz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBHcmlkIHNob3c6MSwgc25hcDoxLCB1bml0czowLCBjb2xvcjpmZjgwODAsIG9yaWdpbjooMCwwKSwgaW50ZXJ2YWw6KDEyNTAwMCwxMjUwMDApPz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBab29tIDY5Pz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBXaWRvd09ycGhhbkNvbnRyb2wgMD8+DQogICA8P3RlbXBsYXRlRGVzaWduZXIgRm9ybVRhcmdldFZlcnNpb24gMzM/Pg0KICAgPD90ZW1wbGF0ZURlc2lnbmVyIERlZmF1bHRMYW5ndWFnZSBKYXZhU2NyaXB0Pz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBEZWZhdWx0UnVuQXQgY2xpZW50Pz4NCiAgIDw/YWNyb2JhdCBKYXZhU2NyaXB0IHN0cmljdFNjb3Bpbmc/Pg0KICAgPD9QREZQcmludE9wdGlvbnMgZW1iZWRWaWV3ZXJQcmVmcyAwPz4NCiAgIDw/UERGUHJpbnRPcHRpb25zIGVtYmVkUHJpbnRPbkZvcm1PcGVuIDA/Pg0KICAgPD9QREZQcmludE9wdGlvbnMgc2NhbGluZ1ByZWZzIDA/Pg0KICAgPD9QREZQcmludE9wdGlvbnMgZW5mb3JjZVNjYWxpbmdQcmVmcyAwPz4NCiAgIDw/UERGUHJpbnRPcHRpb25zIHBhcGVyU291cmNlIDA/Pg0KICAgPD9QREZQcmludE9wdGlvbnMgZHVwbGV4TW9kZSAwPz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBEZWZhdWx0UHJldmlld1R5cGUgaW50ZXJhY3RpdmU/Pg0KICAgPD90ZW1wbGF0ZURlc2lnbmVyIERlZmF1bHRQcmV2aWV3UGFnaW5hdGlvbiBzaW1wbGV4Pz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBYRFBQcmV2aWV3Rm9ybWF0IDIwPz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBEZWZhdWx0Q2FwdGlvbkZvbnRTZXR0aW5ncyBmYWNlOkFyaWFsO3NpemU6MTA7d2VpZ2h0Om5vcm1hbDtzdHlsZTpub3JtYWw/Pg0KICAgPD90ZW1wbGF0ZURlc2lnbmVyIERlZmF1bHRWYWx1ZUZvbnRTZXR0aW5ncyBmYWNlOkFyaWFsO3NpemU6MTA7d2VpZ2h0Om5vcm1hbDtzdHlsZTpub3JtYWw/Pg0KICAgPD90ZW1wbGF0ZURlc2lnbmVyIFJ1bGVycyBob3Jpem9udGFsOjEsIHZlcnRpY2FsOjEsIGd1aWRlbGluZXM6MSwgY3Jvc3NoYWlyczowPz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBTYXZlVGFnZ2VkUERGIDA/Pg0KICAgPD90ZW1wbGF0ZURlc2lnbmVyIFNhdmVQREZXaXRoRW1iZWRkZWRGb250cyAwPz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBTYXZlUERGV2l0aExvZyAwPz4NCiAgIDw/dGVtcGxhdGVEZXNpZ25lciBEZWZhdWx0UHJldmlld0RhdGFGaWxlTmFtZSAuXGp1amFydGVzdC54bWw/PjwvdGVtcGxhdGU+DQo8Y29uZmlnIHhtbG5zPSJodHRwOi8vd3d3LnhmYS5vcmcvc2NoZW1hL3hjaS8zLjAvIj4NCiAgIDxhZ2VudCBuYW1lPSJkZXNpZ25lciI+DQogICAgICA8IS0tICBbMC4ubl0gIC0tPg0KICAgICAgPGRlc3RpbmF0aW9uPnBkZjwvZGVzdGluYXRpb24+DQogICAgICA8cGRmPg0KICAgICAgICAgPCEtLSAgWzAuLm5dICAtLT4NCiAgICAgICAgIDxmb250SW5mby8+DQogICAgICA8L3BkZj4NCiAgIDwvYWdlbnQ+DQogICA8cHJlc2VudD4NCiAgICAgIDwhLS0gIFswLi5uXSAgLS0+DQogICAgICA8cGRmPg0KICAgICAgICAgPCEtLSAgWzAuLm5dICAtLT4NCiAgICAgICAgIDxmb250SW5mbz4NCiAgICAgICAgICAgIDxlbWJlZD4wPC9lbWJlZD4NCiAgICAgICAgIDwvZm9udEluZm8+DQogICAgICAgICA8dGFnZ2VkPjA8L3RhZ2dlZD4NCiAgICAgICAgIDx2ZXJzaW9uPjEuNzwvdmVyc2lvbj4NCiAgICAgICAgIDxhZG9iZUV4dGVuc2lvbkxldmVsPjg8L2Fkb2JlRXh0ZW5zaW9uTGV2ZWw+DQogICAgICA8L3BkZj4NCiAgICAgIDxjb21tb24+DQogICAgICAgICA8ZGF0YT4NCiAgICAgICAgICAgIDx4c2w+DQogICAgICAgICAgICAgICA8dXJpLz4NCiAgICAgICAgICAgIDwveHNsPg0KICAgICAgICAgICAgPG91dHB1dFhTTD4NCiAgICAgICAgICAgICAgIDx1cmkvPg0KICAgICAgICAgICAgPC9vdXRwdXRYU0w+DQogICAgICAgICA8L2RhdGE+DQogICAgICA8L2NvbW1vbj4NCiAgICAgIDx4ZHA+DQogICAgICAgICA8cGFja2V0cz4qPC9wYWNrZXRzPg0KICAgICAgPC94ZHA+DQogICA8L3ByZXNlbnQ+DQo8L2NvbmZpZz4NCjx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNC1jMDA1IDc4LjE1MDA1NSwgMjAxMy8wOC8wNy0yMjo1ODo0NyAgICAgICAgIj4NCiAgIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+DQogICAgICA8cmRmOkRlc2NyaXB0aW9uIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6cGRmPSJodHRwOi8vbnMuYWRvYmUuY29tL3BkZi8xLjMvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6ZGVzYz0iaHR0cDovL25zLmFkb2JlLmNvbS94ZmEvcHJvbW90ZWQtZGVzYy8iIHJkZjphYm91dD0iIj4NCiAgICAgICAgIDx4bXA6TWV0YWRhdGFEYXRlPjIwMTgtMDYtMThUMTQ6MjA6MDVaPC94bXA6TWV0YWRhdGFEYXRlPg0KICAgICAgICAgPHhtcDpDcmVhdG9yVG9vbD5BZG9iZSBMaXZlQ3ljbGUgRGVzaWduZXIgMTEuMDwveG1wOkNyZWF0b3JUb29sPg0KICAgICAgICAgPHBkZjpQcm9kdWNlcj5BZG9iZSBMaXZlQ3ljbGUgRGVzaWduZXIgMTEuMDwvcGRmOlByb2R1Y2VyPg0KICAgICAgICAgPHhtcE1NOkRvY3VtZW50SUQ+dXVpZDo2ZTkwNGMwYS01YjY4LTQxNTktYjExNC0yNDJjNGYzOTFmODQ8L3htcE1NOkRvY3VtZW50SUQ+DQogICAgICAgICA8ZGVzYzp2ZXJzaW9uIHJkZjpwYXJzZVR5cGU9IlJlc291cmNlIj4NCiAgICAgICAgICAgIDxyZGY6dmFsdWU+MTEuMC4xLjIwMTQwMjE4LjEuOTA3MTYyLjkwMzgwMTwvcmRmOnZhbHVlPg0KICAgICAgICAgICAgPGRlc2M6cmVmPi90ZW1wbGF0ZS9zdWJmb3JtWzFdPC9kZXNjOnJlZj4NCiAgICAgICAgIDwvZGVzYzp2ZXJzaW9uPg0KICAgICAgPC9yZGY6RGVzY3JpcHRpb24+DQogICA8L3JkZjpSREY+DQo8L3g6eG1wbWV0YT4NCjx4ZmE6ZGF0YXNldHMgeG1sbnM6eGZhPSJodHRwOi8vd3d3LnhmYS5vcmcvc2NoZW1hL3hmYS1kYXRhLzEuMC8iPg0KICAgPHhmYTpkYXRhIHhmYTpkYXRhTm9kZT0iZGF0YUdyb3VwIi8+DQogICA8ZGQ6ZGF0YURlc2NyaXB0aW9uIHhtbG5zOmRkPSJodHRwOi8vbnMuYWRvYmUuY29tL2RhdGEtZGVzY3JpcHRpb24vIiBkZDpuYW1lPSJmb3JtMSI+DQogICAgICA8Zm9ybTE+DQogICAgICAgICA8VGV4dEZpZWxkMS8+DQogICAgICA8L2Zvcm0xPg0KICAgPC9kZDpkYXRhRGVzY3JpcHRpb24+DQo8L3hmYTpkYXRhc2V0cz4NCjxjb25uZWN0aW9uU2V0IHhtbG5zPSJodHRwOi8vd3d3LnhmYS5vcmcvc2NoZW1hL3hmYS1jb25uZWN0aW9uLXNldC8yLjgvIj4NCiAgIDx4bWxDb25uZWN0aW9uIG5hbWU9IkRhdGFDb25uZWN0aW9uIiBkYXRhRGVzY3JpcHRpb249ImZvcm0xIj4NCiAgICAgIDx1cmk+LlxqdWphcnRlc3QueG1sPC91cmk+DQogICAgICA8P3RlbXBsYXRlRGVzaWduZXIgZmlsZURpZ2VzdCBzaGFIYXNoPSJrcWQwdVNkZU04ak5ic244c2I4akxtYmFvTWc9Ij8+PC94bWxDb25uZWN0aW9uPg0KPC9jb25uZWN0aW9uU2V0Pg0KPGxvY2FsZVNldCB4bWxucz0iaHR0cDovL3d3dy54ZmEub3JnL3NjaGVtYS94ZmEtbG9jYWxlLXNldC8yLjcvIj4NCiAgIDxsb2NhbGUgbmFtZT0iZW5fSU4iIGRlc2M9IkVuZ2xpc2ggKEluZGlhKSI+DQogICAgICA8Y2FsZW5kYXJTeW1ib2xzIG5hbWU9ImdyZWdvcmlhbiI+DQogICAgICAgICA8bW9udGhOYW1lcz4NCiAgICAgICAgICAgIDxtb250aD5KYW51YXJ5PC9tb250aD4NCiAgICAgICAgICAgIDxtb250aD5GZWJydWFyeTwvbW9udGg+DQogICAgICAgICAgICA8bW9udGg+TWFyY2g8L21vbnRoPg0KICAgICAgICAgICAgPG1vbnRoPkFwcmlsPC9tb250aD4NCiAgICAgICAgICAgIDxtb250aD5NYXk8L21vbnRoPg0KICAgICAgICAgICAgPG1vbnRoPkp1bmU8L21vbnRoPg0KICAgICAgICAgICAgPG1vbnRoPkp1bHk8L21vbnRoPg0KICAgICAgICAgICAgPG1vbnRoPkF1Z3VzdDwvbW9udGg+DQogICAgICAgICAgICA8bW9udGg+U2VwdGVtYmVyPC9tb250aD4NCiAgICAgICAgICAgIDxtb250aD5PY3RvYmVyPC9tb250aD4NCiAgICAgICAgICAgIDxtb250aD5Ob3ZlbWJlcjwvbW9udGg+DQogICAgICAgICAgICA8bW9udGg+RGVjZW1iZXI8L21vbnRoPg0KICAgICAgICAgPC9tb250aE5hbWVzPg0KICAgICAgICAgPG1vbnRoTmFtZXMgYWJicj0iMSI+DQogICAgICAgICAgICA8bW9udGg+SmFuPC9tb250aD4NCiAgICAgICAgICAgIDxtb250aD5GZWI8L21vbnRoPg0KICAgICAgICAgICAgPG1vbnRoPk1hcjwvbW9udGg+DQogICAgICAgICAgICA8bW9udGg+QXByPC9tb250aD4NCiAgICAgICAgICAgIDxtb250aD5NYXk8L21vbnRoPg0KICAgICAgICAgICAgPG1vbnRoPkp1bjwvbW9udGg+DQogICAgICAgICAgICA8bW9udGg+SnVsPC9tb250aD4NCiAgICAgICAgICAgIDxtb250aD5BdWc8L21vbnRoPg0KICAgICAgICAgICAgPG1vbnRoPlNlcDwvbW9udGg+DQogICAgICAgICAgICA8bW9udGg+T2N0PC9tb250aD4NCiAgICAgICAgICAgIDxtb250aD5Ob3Y8L21vbnRoPg0KICAgICAgICAgICAgPG1vbnRoPkRlYzwvbW9udGg+DQogICAgICAgICA8L21vbnRoTmFtZXM+DQogICAgICAgICA8ZGF5TmFtZXM+DQogICAgICAgICAgICA8ZGF5PlN1bmRheTwvZGF5Pg0KICAgICAgICAgICAgPGRheT5Nb25kYXk8L2RheT4NCiAgICAgICAgICAgIDxkYXk+VHVlc2RheTwvZGF5Pg0KICAgICAgICAgICAgPGRheT5XZWRuZXNkYXk8L2RheT4NCiAgICAgICAgICAgIDxkYXk+VGh1cnNkYXk8L2RheT4NCiAgICAgICAgICAgIDxkYXk+RnJpZGF5PC9kYXk+DQogICAgICAgICAgICA8ZGF5PlNhdHVyZGF5PC9kYXk+DQogICAgICAgICA8L2RheU5hbWVzPg0KICAgICAgICAgPGRheU5hbWVzIGFiYnI9IjEiPg0KICAgICAgICAgICAgPGRheT5TdW48L2RheT4NCiAgICAgICAgICAgIDxkYXk+TW9uPC9kYXk+DQogICAgICAgICAgICA8ZGF5PlR1ZTwvZGF5Pg0KICAgICAgICAgICAgPGRheT5XZWQ8L2RheT4NCiAgICAgICAgICAgIDxkYXk+VGh1PC9kYXk+DQogICAgICAgICAgICA8ZGF5PkZyaTwvZGF5Pg0KICAgICAgICAgICAgPGRheT5TYXQ8L2RheT4NCiAgICAgICAgIDwvZGF5TmFtZXM+DQogICAgICAgICA8bWVyaWRpZW1OYW1lcz4NCiAgICAgICAgICAgIDxtZXJpZGllbT5BTTwvbWVyaWRpZW0+DQogICAgICAgICAgICA8bWVyaWRpZW0+UE08L21lcmlkaWVtPg0KICAgICAgICAgPC9tZXJpZGllbU5hbWVzPg0KICAgICAgICAgPGVyYU5hbWVzPg0KICAgICAgICAgICAgPGVyYT5CQzwvZXJhPg0KICAgICAgICAgICAgPGVyYT5BRDwvZXJhPg0KICAgICAgICAgPC9lcmFOYW1lcz4NCiAgICAgIDwvY2FsZW5kYXJTeW1ib2xzPg0KICAgICAgPGRhdGVQYXR0ZXJucz4NCiAgICAgICAgIDxkYXRlUGF0dGVybiBuYW1lPSJmdWxsIj5FRUVFIEQgTU1NTSBZWVlZPC9kYXRlUGF0dGVybj4NCiAgICAgICAgIDxkYXRlUGF0dGVybiBuYW1lPSJsb25nIj5EIE1NTU0gWVlZWTwvZGF0ZVBhdHRlcm4+DQogICAgICAgICA8ZGF0ZVBhdHRlcm4gbmFtZT0ibWVkIj5ERC1NTU0tWVk8L2RhdGVQYXR0ZXJuPg0KICAgICAgICAgPGRhdGVQYXR0ZXJuIG5hbWU9InNob3J0Ij5ERC9NTS9ZWTwvZGF0ZVBhdHRlcm4+DQogICAgICA8L2RhdGVQYXR0ZXJucz4NCiAgICAgIDx0aW1lUGF0dGVybnM+DQogICAgICAgICA8dGltZVBhdHRlcm4gbmFtZT0iZnVsbCI+aDpNTTpTUyBBIFo8L3RpbWVQYXR0ZXJuPg0KICAgICAgICAgPHRpbWVQYXR0ZXJuIG5hbWU9ImxvbmciPmg6TU06U1MgQSBaPC90aW1lUGF0dGVybj4NCiAgICAgICAgIDx0aW1lUGF0dGVybiBuYW1lPSJtZWQiPmg6TU06U1MgQTwvdGltZVBhdHRlcm4+DQogICAgICAgICA8dGltZVBhdHRlcm4gbmFtZT0ic2hvcnQiPmg6TU0gQTwvdGltZVBhdHRlcm4+DQogICAgICA8L3RpbWVQYXR0ZXJucz4NCiAgICAgIDxkYXRlVGltZVN5bWJvbHM+R3lNZGtIbXNTRURGd1dhaEt6WjwvZGF0ZVRpbWVTeW1ib2xzPg0KICAgICAgPG51bWJlclBhdHRlcm5zPg0KICAgICAgICAgPG51bWJlclBhdHRlcm4gbmFtZT0ibnVtZXJpYyI+eix6eix6ejkuenp6PC9udW1iZXJQYXR0ZXJuPg0KICAgICAgICAgPG51bWJlclBhdHRlcm4gbmFtZT0iY3VycmVuY3kiPiQgeix6eix6ejkuOTk8L251bWJlclBhdHRlcm4+DQogICAgICAgICA8bnVtYmVyUGF0dGVybiBuYW1lPSJwZXJjZW50Ij56LHp6LHp6OSU8L251bWJlclBhdHRlcm4+DQogICAgICA8L251bWJlclBhdHRlcm5zPg0KICAgICAgPG51bWJlclN5bWJvbHM+DQogICAgICAgICA8bnVtYmVyU3ltYm9sIG5hbWU9ImRlY2ltYWwiPi48L251bWJlclN5bWJvbD4NCiAgICAgICAgIDxudW1iZXJTeW1ib2wgbmFtZT0iZ3JvdXBpbmciPiw8L251bWJlclN5bWJvbD4NCiAgICAgICAgIDxudW1iZXJTeW1ib2wgbmFtZT0icGVyY2VudCI+JTwvbnVtYmVyU3ltYm9sPg0KICAgICAgICAgPG51bWJlclN5bWJvbCBuYW1lPSJtaW51cyI+LTwvbnVtYmVyU3ltYm9sPg0KICAgICAgICAgPG51bWJlclN5bWJvbCBuYW1lPSJ6ZXJvIj4wPC9udW1iZXJTeW1ib2w+DQogICAgICA8L251bWJlclN5bWJvbHM+DQogICAgICA8Y3VycmVuY3lTeW1ib2xzPg0KICAgICAgICAgPGN1cnJlbmN5U3ltYm9sIG5hbWU9InN5bWJvbCI+UnMuPC9jdXJyZW5jeVN5bWJvbD4NCiAgICAgICAgIDxjdXJyZW5jeVN5bWJvbCBuYW1lPSJpc29uYW1lIj5JTlI8L2N1cnJlbmN5U3ltYm9sPg0KICAgICAgICAgPGN1cnJlbmN5U3ltYm9sIG5hbWU9ImRlY2ltYWwiPi48L2N1cnJlbmN5U3ltYm9sPg0KICAgICAgPC9jdXJyZW5jeVN5bWJvbHM+DQogICAgICA8dHlwZWZhY2VzPg0KICAgICAgICAgPHR5cGVmYWNlIG5hbWU9Ik15cmlhZCBQcm8iLz4NCiAgICAgICAgIDx0eXBlZmFjZSBuYW1lPSJNaW5pb24gUHJvIi8+DQogICAgICAgICA8dHlwZWZhY2UgbmFtZT0iQ291cmllciBTdGQiLz4NCiAgICAgICAgIDx0eXBlZmFjZSBuYW1lPSJBZG9iZSBQaSBTdGQiLz4NCiAgICAgICAgIDx0eXBlZmFjZSBuYW1lPSJBZG9iZSBIZWJyZXciLz4NCiAgICAgICAgIDx0eXBlZmFjZSBuYW1lPSJBZG9iZSBBcmFiaWMiLz4NCiAgICAgICAgIDx0eXBlZmFjZSBuYW1lPSJBZG9iZSBUaGFpIi8+DQogICAgICAgICA8dHlwZWZhY2UgbmFtZT0iS296dWthIEdvdGhpYyBQcm8tVkkgTSIvPg0KICAgICAgICAgPHR5cGVmYWNlIG5hbWU9IktvenVrYSBNaW5jaG8gUHJvLVZJIFIiLz4NCiAgICAgICAgIDx0eXBlZmFjZSBuYW1lPSJBZG9iZSBNaW5nIFN0ZCBMIi8+DQogICAgICAgICA8dHlwZWZhY2UgbmFtZT0iQWRvYmUgU29uZyBTdGQgTCIvPg0KICAgICAgICAgPHR5cGVmYWNlIG5hbWU9IkFkb2JlIE15dW5nam8gU3RkIE0iLz4NCiAgICAgICAgIDx0eXBlZmFjZSBuYW1lPSJBZG9iZSBEZXZhbmFnYXJpIi8+DQogICAgICA8L3R5cGVmYWNlcz4NCiAgIDwvbG9jYWxlPg0KPC9sb2NhbGVTZXQ+PC94ZHA6eGRwPg==\",\"xmlData\":\"PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCjxmb3JtMT4NCiAgIDxUZXh0RmllbGQxPkp1amFyIHRlc3Q8L1RleHRGaWVsZDE+DQo8L2Zvcm0xPg==\"}";
			
			
			InputStream fConfigProp = null;
			Properties pConfig = new Properties();
			
			/////////----------------------------------------------------
			String sXDPTemplatePath = "";
			//String sXMLDataLabelVal = "xmlDataLabel";
			//String sXMLDataLabel = "";
			//String sXMLDataPath = "";
			//getOutboundDelivery("80000000");
			String tmpDeliveryNum = "80000000";
			OutBoundDelivery oDelivery = new  OutBoundDelivery(tmpDeliveryNum);
			
			Multimap<Integer, ArrayList<String>> mmapHU = CalculateHandlingUnit(oDelivery);
			Gson mapgson = new Gson();  
			String mapjson = mapgson.toJson(mmapHU.asMap());
			
			//return mapjson;
			
			
			
			
			
			
			
			try {
				File directory1 = new File("./");
				System.out.println("Path-------------" + directory1.getAbsolutePath());
				
				fConfigProp = this.getClass().getClassLoader().getResourceAsStream("config.properties");
				System.out.println("Path-------------" + directory1.getAbsolutePath());
				System.out.println("-----------------------------" + fConfigProp.toString());
				pConfig.load(fConfigProp);
				
				//sXMLDataLabel = pConfig.getProperty(sXMLDataLabelVal);
				if(sXMLDataLabel == "" || sXMLDataLabel == null)
				{
					sXMLDataLabel = "B10Master";
				}
				sXDPTemplatePath = pConfig.getProperty(sXMLDataLabel+"Template");
				
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
			
			
			
			B10MasterLabel b10MasterLabel = new B10MasterLabel(); 
			S4HDataRetriever oS4HRet = new S4HDataRetriever();
			
			b10MasterLabel.setMaterialbyCustomer("4335B76h"); // No Output in API
			b10MasterLabel.setActualDeliveryQuantity(oS4HRet.ActualDelQuantity);  // SingleDeliveryCommand - OutboudDeliveryServ
			//b10MasterLabel.setActualDeliveryQuantity("Testing");  // SingleDeliveryCommand - OutboudDeliveryServ
			b10MasterLabel.setOrderID("8873654");				// SingleDelivery - OutboudDeliveryServ
			b10MasterLabel.setReceivingPlant("2954B");	// No Output in API
			b10MasterLabel.setWarehouseGate("4B");		// No Output in API
			b10MasterLabel.setAccountByCustomer(oS4HRet.AcctbyCust);	// GetSingleCustomerCompanyCommand - OutboudDeliveryServ
			//b10MasterLabel.setAccountByCustomer("TestingCust");	// GetSingleCustomerCompanyCommand - OutboudDeliveryServ
			
			
			B10MixedLabel b10MixedLabel = new B10MixedLabel();
			b10MixedLabel.setOrderID("8873654");				// SingleDelivery - OutboudDeliveryServ
			b10MixedLabel.setReceivingPlant("2954B");	// No Output in API
			b10MixedLabel.setWarehouseGate("4B");		// No Output in API
			b10MasterLabel.setAccountByCustomer(oS4HRet.AcctbyCust);	// GetSingleCustomerCompanyCommand - OutboudDeliveryServ
			//b10MasterLabel.setAccountByCustomer("TestingCust");	
			
			Label label=null;
			
			if(sXMLDataLabel.equals(new String("B10Master"))) 
			{
				label = new Label();
				label.setB10MasterLabel(b10MasterLabel);
			}
			else if(sXMLDataLabel.equals(new String("B10Mixed"))) 
			{
				label = new Label();	
				label.setB10MixedLabel(b10MixedLabel);
			}
			else {
				label = new Label();
				label.setB10MasterLabel(b10MasterLabel);
			}
						
			XMLLabelGenerator oLabelXML = new XMLLabelGenerator();
			String sLabelXMLStr = oLabelXML.GenerateXML(label);
			
			String sBase64DataLabel = Base64.getEncoder().encodeToString((sLabelXMLStr).getBytes());
			System.out.println("sBase64DataLabel -------------" + sBase64DataLabel);
			String sFileXDPTemplate ="";
			System.out.println("sXMLDataLabel-------------"+ sXMLDataLabel);
			System.out.println("sXDPTemplatePath-------------"+ sXDPTemplatePath);
			
			//try {
				 //File fDataXMLFile = new File(sXDPTemplatePath);
				File directory = new File("");
				System.out.println(directory.getAbsolutePath());
				//System.out.println("dsjhsjkdhskdhskhdk ---------------------" + Paths.get("/"));
				InputStream fTemplate = this.getClass().getClassLoader().getResourceAsStream(sXDPTemplatePath); 
				sFileXDPTemplate = getStringFromInputStream(fTemplate);			
				System.out.println("sFileXDPTemplate-----------" + sFileXDPTemplate);
			//}catch(IOException eio) {
			//	eio.printStackTrace();
			//}
			//sFileXDPTemplate = new String(Files.readAllBytes(Paths.get(sXDPTemplatePath)), StandardCharsets.UTF_8);
			String sBase64XDPTemplate =  Base64.getEncoder().encodeToString((sFileXDPTemplate).getBytes());
			System.out.println("sBase64XDPTemplate -------------" + sBase64XDPTemplate);
				
			
			ADSRenderJsonData oJsonData = new ADSRenderJsonData(sBase64XDPTemplate, sBase64DataLabel);
			Gson oADSGSON = new Gson();
			String sJSON2 = oADSGSON.toJson(oJsonData);
			
			
			
			/*
			Properties prop = new Properties();
			InputStream fInput = null;
			String sXDPTemplate = "";
			String sXMLData = "";
			
			try {

				//fInput = new FileInputStream("\\config.properties");
				//InputStream config = ClassLoader.getSystemResourceAsStream("config.xml"); 
				fInput = this.getClass().getClassLoader().getResourceAsStream("config.properties");
				
				// load a properties file
				prop.load(fInput);

				// get the property value and print it out
				System.out.println("xdpTemplate1 " + prop.getProperty("xdpTemplate1"));
				System.out.println("xmlData1" + prop.getProperty("xmlData1"));
				sXDPTemplate = prop.getProperty("xdpTemplate1");
				sXMLData = prop.getProperty("xmlData1");

			} catch (IOException ex) {
				ex.printStackTrace();
			} finally {
				if (fInput != null) {
					try {
						fInput.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			String sJSON = "";
			JSONObject oJSON;
			try {
			oJSON = new JSONObject();

			oJSON.put("xdpTemplate",sXDPTemplate);
			oJSON.put("xmlData",sXMLData);
			
			StringWriter oJSONOut = new StringWriter();
			oJSON.writeJSONString(oJSONOut);

			sJSON = oJSONOut.toString();
			
			} catch (IOException ex) {
				ex.printStackTrace();
			} */
			
			/*finally {
				oJSON= null; //.close();
			}*/
			/* TARGET URL AND JSON */
			String url = "https://adsrestapiformsprocessing-a7762c010.hana.ondemand.com/ads.restapi/v1/adsRender/pdf";
			
			

			/* HTTPCLIENT AND HTTPPOST OOBJECT */
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost request = new HttpPost(url);

			/* ADD HEADER INFO */
			request.addHeader("TraceLevel", "2");
			request.addHeader("Authorization", "Bearer 74950538c14501f8917cd45c563db11");
			request.addHeader("Content-Type", "application/json");
			

			/* PROXY CONFIG 
			HttpHost target = new HttpHost("adsrestapiformsprocessing-a7762c010.hana.ondemand.com",443,"HTTPS");
			RequestConfig config = RequestConfig.custom().setProxy(target).build();
			request.setConfig(config);
			 */
			/* JSON AS STRINGENTITY */
			StringEntity input = null;
			try {
				System.out.println("sJSON2 --------------------------------------------------------------" + sJSON2);
				//input = new StringEntity(sJSON1);
				input = new StringEntity(sJSON2);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			request.setEntity(input);
			System.out.println("request -------------" + request.toString());
			
			/* SEND AND RETRIEVE RESPONSE */
			HttpResponse response = null;
			try {
				response = httpClient.execute(request);
			} catch (IOException e) {
				e.printStackTrace();
			}

			/* RESPONSE AS JSON STRING */
			String result = null;
			try {
				result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
				System.out.println("result -------------------------------" + result);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return result;
		}
		
		private static String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return sb.toString();

		}
		
		private static Multimap<Integer, ArrayList<String>> CalculateHandlingUnit(OutBoundDelivery oOutBoundDelivery) {
			
			int iHUCapacity = 5;
			int iHUQty = 0;
			//ArrayList<String> arrPrdHU =new ArrayList<String>(new ArrayList<String>());
			Multimap<Integer, ArrayList<String>> arrPrdHU = LinkedHashMultimap.create();
			//int iPrdHUCount;
			//int iHURemainingCapacity;
			//int iPrdHURemaining = 0;
			//int itmp = 0;
			/*
			if(oOutBoundDelivery.DeliveryItem.size() == 1)
			{
				
			}
			else if(oOutBoundDelivery.DeliveryItem.size() > 1)
			{
				
			}
			else 
			{
				
			}
			*/
			
			ListIterator <OutbDeliveryItem> itrOBDI = oOutBoundDelivery.DeliveryItem.listIterator();
			int iRemainingHUCap = 0;
			int iHUNum = 0;
			ArrayList<String> tmpHUArray = new ArrayList<String>();
			while(itrOBDI.hasNext())
			{
				
				OutbDeliveryItem oOutBoundDeliveryItem = itrOBDI.next();
				Float iQty = oOutBoundDeliveryItem.getActualDeliveryQuantity().floatValue();
				String sMaterialName = oOutBoundDeliveryItem.getMaterial();
				int itmpQty = iQty.intValue();
				
				while(itmpQty > 0) {
					if(iRemainingHUCap>0)
					{
						if(iRemainingHUCap < itmpQty ) 
						{
							itmpQty = itmpQty - iRemainingHUCap;
							
							tmpHUArray.clear();
							tmpHUArray.add(0, sMaterialName);
							arrPrdHU.put(iHUNum, tmpHUArray);
							iHUNum = iHUNum + 1;
							
							iRemainingHUCap = 0;
							
						}else {
							iRemainingHUCap = iRemainingHUCap - itmpQty;
							tmpHUArray.clear();
							tmpHUArray.add(0, sMaterialName);
							arrPrdHU.put(iHUNum, tmpHUArray);
							itmpQty = 0;	
						}
						
					}
					else if(itmpQty < iHUCapacity ) 
					{
						iRemainingHUCap = iHUCapacity - itmpQty;
						itmpQty = 0;						
						tmpHUArray.clear();
						tmpHUArray.add(0, sMaterialName);
						arrPrdHU.put(iHUNum, tmpHUArray);
						
						
					}
					else
					{
						itmpQty = itmpQty - iHUCapacity;
						tmpHUArray.clear();
						tmpHUArray.add(0, sMaterialName);
						arrPrdHU.put(iHUNum, tmpHUArray);
						iRemainingHUCap =0;
						iHUNum = iHUNum+1;
						
					}
					
					
					}
				}
				
				
			/*	
				iPrdHUCount = (int)Math.ceil(iQty/iHUCapacity);
				
				iPrdHURemaining = iQty%iHUCapacity;
				iHURemainingCapacity = iHUCapacity - iPrdHURemaining;
				ArrayList<String> tmpArray = new ArrayList<String>();
					//{oOutBoundDeliveryItem.getMaterial(),iPrdHUCount.toString(),iPrdHURemaining.toString()};
				 tmpArray.add(0, oOutBoundDeliveryItem.getMaterial() );
				 tmpArray.add(1, iPrdHUCount.toString() );
				 tmpArray.add(2, iPrdHURemaining.toString() );
				 tmpArray.add(3, iHURemainingCapacity.toString());
				 
				 arrPrdHU.addAll(itmp, tmpArray);
				 itmp = itmp +1;
			*/	 
			
			return arrPrdHU;
			}
			
			
			
			
			
		//}
		
	/*	private ADSRenderJsonData ADSRenderJsonData(String sXDPTemplatePath, String sXMLDataPath) {
			// TODO Auto-generated method stub
			return null;
		}
*/
		/*public static void main(String[] args) {
			AdsRestClient restClient = new AdsRestClient();
			String response = restClient.callService();
			System.out.println(response);
		}*/
}