1. Per-request network checker. 
-- In some cases you doneed to ignore some network types for certain requests.
Or you may not want to check a network connection at all for example when performing
some local calculations which do not require the connection. Yes, you could do it
by creating a separate service for this purpose with it's own network checker 
but hey there's no need to create a lot of Services.

2. Per-request retry policy.
-- Explanation is the same as for item 1.

3. Inject a reference to a Service inside a Request.
-- Sometimes you do need to have that refference
